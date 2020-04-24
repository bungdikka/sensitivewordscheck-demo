package com.moonlight.validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.moonlight.util.SensitiveWordsChecker;

public class SensitiveWordsValidator implements ConstraintValidator<Sensitive, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value==null||!StringUtils.hasText(value.trim())){
			return true;
		}
		value = value.trim();
		//获取敏感词配置，这里为简化示例
		Set<String> sensitiveWordsSet = new HashSet<>();
		sensitiveWordsSet.add("法轮功");
		HashMap sensitiveWordsDFA = SensitiveWordsChecker.getSingleton().addSensitiveWordToHashMap(sensitiveWordsSet);
		if(SensitiveWordsChecker.getSingleton().isContaintSensitiveWord(sensitiveWordsDFA,value,2)){
			return false;
		}
		return true;
	}

}
