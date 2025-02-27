package MusinsaClone.product.dto;

import java.util.List;

public record OptionGroup(
        String optionName, // 옵션 그룹명 (예: 색상)
        List<OptionValue> optionValues, // 옵션 값 리스트 (예: 빨강, 파랑)
        List<OptionGroup> subOptions // 서브 옵션 리스트 (예: 사이즈 옵션)
) {
}
