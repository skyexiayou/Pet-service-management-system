package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.medical.MedicalDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MedicalMapper extends BaseMapper<MedicalDO> {
    MedicalDO selectMedicalById(@Param("medicalId") Integer medicalId);
    List<MedicalDO> selectMedicalsByType(@Param("medicalType") String medicalType);
    List<MedicalDO> selectAllMedicals();
}