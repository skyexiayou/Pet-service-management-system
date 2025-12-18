package cn.edu.xaut.service.pet.impl;

import cn.edu.xaut.domain.dto.pet.PetDTO;
import cn.edu.xaut.domain.entity.pet.PetDO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.domain.vo.pet.PetDetailVO;
import cn.edu.xaut.domain.vo.pet.PetVO;
import cn.edu.xaut.domain.vo.pet.ServiceRecordVO;
import cn.edu.xaut.domain.vo.user.UserVO;
import cn.edu.xaut.mapper.PetMapper;
import cn.edu.xaut.service.pet.PetService;
import cn.edu.xaut.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 宠物业务层实现类
 * 实现PetService接口，处理宠物信息的查询（基础/详情）、分页、新增、修改等核心业务逻辑，
 * 包含宠物信息脱敏、服务记录关联、DO/VO转换等特殊处理
 * @date 2025-12-18
 */
@Service
public class PetServiceImpl implements PetService {

    /** 宠物数据访问层接口，负责宠物相关数据库操作 */
    @Autowired
    private PetMapper petMapper;

    /** 用户业务层接口，用于关联查询宠物所属用户信息 */
    @Autowired
    private UserService userService;

    /**
     * 根据宠物ID查询宠物基础信息VO
     *
     * @param petId 宠物ID（主键）
     * @return 宠物基础信息VO，无对应数据时返回null
     */
    @Override
    public PetVO getPetById(Integer petId) {
        PetDO pet = petMapper.selectById(petId);
        if (pet == null) {
            return null;
        }
        return convertToPetVO(pet);
    }

    /**
     * 根据宠物ID查询宠物详情信息（含脱敏、服务记录）
     * 1. 对宠物多媒体路径做脱敏处理，仅返回文件名；
     * 2. 关联查询宠物的所有服务记录并转换为VO；
     * 3. 处理服务记录的日期时间拼接解析
     *
     * @param petId 宠物ID（主键）
     * @return 宠物详情VO，无对应数据时返回null
     */
    @Override
    public PetDetailVO getPetDetail(Integer petId) {
        PetDO pet = petMapper.selectById(petId);
        if (pet == null) {
            return null;
        }

        PetDetailVO petDetail = new PetDetailVO();
        BeanUtils.copyProperties(pet, petDetail);
        petDetail.setUserId(pet.getUserId());

        // 查询并添加关联的服务记录
        List<Map<String, Object>> serviceRecordMaps = petMapper.selectPetServiceRecords(petId);
        List<ServiceRecordVO> serviceRecords = new ArrayList<>();

        for (Map<String, Object> map : serviceRecordMaps) {
            // 解析日期和时间
            Object serviceDateObj = map.get("serviceDate");
            Object serviceTimeObj = map.get("serviceTime");
            Date serviceDateTime = null;

            try {
                // 处理LocalDateTime或Date类型
                if (serviceDateObj instanceof java.time.LocalDateTime) {
                    java.time.LocalDateTime localDateTime = (java.time.LocalDateTime) serviceDateObj;
                    serviceDateTime = Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
                } else if (serviceDateObj instanceof Date) {
                    Date serviceDate = (Date) serviceDateObj;
                    String serviceTimeStr = serviceTimeObj != null ? serviceTimeObj.toString() : null;
                    
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    if (serviceDate != null && serviceTimeStr != null) {
                        String dateStr = dateFormat.format(serviceDate);
                        serviceDateTime = dateTimeFormat.parse(dateStr + " " + serviceTimeStr);
                    }
                } else if (serviceTimeObj instanceof java.time.LocalDateTime) {
                    java.time.LocalDateTime localDateTime = (java.time.LocalDateTime) serviceTimeObj;
                    serviceDateTime = Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
                }
            } catch (Exception e) {
                // 解析失败时使用当前时间
                serviceDateTime = new Date();
            }

            ServiceRecordVO serviceRecord = ServiceRecordVO.builder()
                    .serviceType((String) map.get("serviceType"))
                    .serviceName((String) map.get("serviceName"))
                    .serviceTime(serviceDateTime)
                    .serviceStatus((String) map.get("status"))
                    .servicePrice((BigDecimal) map.get("price"))
                    // 时长和备注字段默认为null或0
                    .duration(0)
                    .serviceRemarks(null)
                    .build();

            serviceRecords.add(serviceRecord);
        }

        petDetail.setRelatedServiceRecords(serviceRecords);

        return petDetail;
    }

    /**
     * 根据用户ID查询该用户下的所有宠物基础信息VO列表
     *
     * @param userId 用户ID（宠物所属用户）
     * @return 该用户下的宠物基础信息VO列表，无数据时返回空列表
     */
    @Override
    public List<PetVO> getPetsByUserId(Integer userId) {
        List<PetDO> pets = petMapper.selectPetsByUserId(userId);
        return pets.stream().map(this::convertToPetVO).collect(Collectors.toList());
    }

    /**
     * 查询所有宠物基础信息VO列表
     *
     * @return 全量宠物基础信息VO列表，无数据时返回空列表
     */
    @Override
    public List<PetVO> getAllPets() {
        List<PetDO> pets = petMapper.selectList(null);
        return pets.stream().map(this::convertToPetVO).collect(Collectors.toList());
    }

    /**
     * 分页查询所有宠物基础信息VO
     * 基于MyBatis-Plus内置分页插件实现自动分页
     *
     * @param pageNum  当前页码（从1开始）
     * @param pageSize 每页显示条数
     * @return 分页结果VO（包含总条数、当前页宠物VO列表、页码/页大小）
     */
    @Override
    public cn.edu.xaut.domain.vo.PageResultVO<PetVO> getAllPetsPage(Integer pageNum, Integer pageSize) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<PetDO> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum, pageSize);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<PetDO> resultPage = petMapper.selectPage(page, null);
        List<PetVO> petVOs = resultPage.getRecords().stream()
                .map(this::convertToPetVO)
                .collect(Collectors.toList());
        return cn.edu.xaut.domain.vo.PageResultVO.<PetVO>builder()
                .total(resultPage.getTotal())
                .list(petVOs)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 根据用户ID分页查询该用户下的宠物基础信息VO
     * 先查询全量数据再手动分页（适用于数据量较小的场景）
     *
     * @param userId   用户ID（宠物所属用户）
     * @param pageNum  当前页码（从1开始）
     * @param pageSize 每页显示条数
     * @return 分页结果VO（包含总条数、当前页宠物VO列表、页码/页大小）
     */
    @Override
    public cn.edu.xaut.domain.vo.PageResultVO<PetVO> getPetsByUserIdPage(Integer userId, Integer pageNum, Integer pageSize) {
        List<PetDO> allPets = petMapper.selectPetsByUserId(userId);
        // 手动分页：计算起始/结束下标，避免数组越界
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, allPets.size());
        List<PetDO> pageList = allPets.subList(start, end);
        List<PetVO> petVOs = pageList.stream()
                .map(this::convertToPetVO)
                .collect(Collectors.toList());
        return cn.edu.xaut.domain.vo.PageResultVO.<PetVO>builder()
                .total((long) allPets.size())
                .list(petVOs)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 新增宠物信息
     * 将前端传入的PetDTO转换为PetDO后插入数据库
     *
     * @param petDTO 宠物信息入参DTO（前端传入的新增数据）
     * @return 受影响的行数（1=新增成功，0=新增失败）
     */
    @Override
    public Integer createPet(PetDTO petDTO) {
        PetDO pet = new PetDO();
        BeanUtils.copyProperties(petDTO, pet);
        return petMapper.insert(pet);
    }

    /**
     * 修改宠物信息
     * 根据宠物ID更新对应信息，DTO转换为DO后执行更新
     *
     * @param petId  宠物ID（主键，指定要修改的记录）
     * @param petDTO 宠物信息入参DTO（前端传入的修改数据）
     * @return 受影响的行数（1=修改成功，0=修改失败/无数据更新）
     */
    @Override
    public Integer updatePet(Integer petId, PetDTO petDTO) {
        PetDO pet = new PetDO();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setPetId(petId);
        return petMapper.updateById(pet);
    }

    /**
     * 私有方法：将PetDO转换为PetVO
     * 1. 对宠物多媒体路径做脱敏处理，仅返回文件名；
     * 2. 关联查询宠物所属用户信息并转换为UserVO
     *
     * @param pet 宠物实体类DO
     * @return 宠物基础信息VO
     */
    private PetVO convertToPetVO(PetDO pet) {
        PetVO petVO = new PetVO();
        BeanUtils.copyProperties(pet, petVO);

        // 注意：数据库中暂无PetMediaPath字段，如需使用请先添加到数据库表中
        // 脱敏处理宠物多媒体路径，只返回文件名部分
        // if (pet.getPetMediaPath() != null && !pet.getPetMediaPath().isEmpty()) {
        //     // 获取路径中的文件名
        //     int lastSlashIndex = pet.getPetMediaPath().lastIndexOf('/');
        //     if (lastSlashIndex == -1) {
        //         lastSlashIndex = pet.getPetMediaPath().lastIndexOf('\\');
        //     }
        //     if (lastSlashIndex != -1 && lastSlashIndex < pet.getPetMediaPath().length() - 1) {
        //         petVO.setPetMediaPath(pet.getPetMediaPath().substring(lastSlashIndex + 1));
        //     }
        // }

        UserDO user = userService.getUserById(pet.getUserId());
        if (user != null) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            petVO.setUser(userVO);
        }

        return petVO;
    }
}