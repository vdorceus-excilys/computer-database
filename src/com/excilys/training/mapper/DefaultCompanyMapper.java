package com.excilys.training.mapper;

import com.excilys.training.mapper.dto.DataTransferObject;
import com.excilys.training.mapper.dto.DefaultCompanySkin;
import com.excilys.training.model.Company;

public class DefaultCompanyMapper implements Mapper<Company,DataTransferObject<Company>> {

	@Override
	public Company forward(DataTransferObject<Company> pojo) {
		// TODO Auto-generated method stub
		DefaultCompanySkin po = (DefaultCompanySkin) pojo;
		Company company = new Company();
		company.setId(Long.parseLong(po.getId()));
		company.setName(po.getName());
		return  company;
	}

	@Override
	public DataTransferObject<Company> reverse(Company pojo) {
		// TODO Auto-generated method stub
		DefaultCompanySkin skin = new DefaultCompanySkin();
		skin.setId(pojo.getId().toString());
		skin.setName(pojo.getName());
		return skin;
	}

}
