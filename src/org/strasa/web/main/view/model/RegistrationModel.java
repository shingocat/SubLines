package org.strasa.web.main.view.model;

import java.util.List;

import org.strasa.middleware.manager.CountryManagerImpl;
import org.strasa.middleware.model.Country;
import org.strasa.middleware.model.DbUser;

public class RegistrationModel  {

	private RegistrationCapthaGenerator rsg = new RegistrationCapthaGenerator(4);
	DbUser user = new DbUser();
	private String retypedPassword;
	private String captcha = rsg.getRandomString();
	private String captchaInput;
	private List<Country> countries=new CountryManagerImpl().getAllCountry();

	public RegistrationCapthaGenerator getRsg() {
		return rsg;
	}
	public void setRsg(RegistrationCapthaGenerator rsg) {
		this.rsg = rsg;
	}
	public String getRetypedPassword() {
		return retypedPassword;
	}
	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}

	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getCaptchaInput() {
		return captchaInput;
	}
	public void setCaptchaInput(String captchaInput) {
		this.captchaInput = captchaInput;
	}
	public DbUser getUser() {
		return user;
	}
	public void setUser(DbUser user) {
		this.user = user;
	}
	
    public void regenerateCaptcha() {
        this.captcha = rsg.getRandomString();
    }
	public List<Country> getCountries() {
		return countries;
	}
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	
}
