package com.mumu.meishijia.model;

/**
 * 所在地
 * 
 * @author dongmei zhang
 * @createdate 2015-2-11
 */
public class RegionModel {
	private String id;
	private String parentid;
	private String zipcode;
	private String name;
	private String level;
	private String listorder;
	private String name_en;
	private String short_code;
	private String country_id;
	private String confirm_status;
	private String confirm_by;
	private String confirm_time;
	private String sortLetters;
	private boolean selected;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getListorder() {
		return listorder;
	}

	public void setListorder(String listorder) {
		this.listorder = listorder;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getShort_code() {
		return short_code;
	}

	public void setShort_code(String short_code) {
		this.short_code = short_code;
	}

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getConfirm_status() {
		return confirm_status;
	}

	public void setConfirm_status(String confirm_status) {
		this.confirm_status = confirm_status;
	}

	public String getConfirm_by() {
		return confirm_by;
	}

	public void setConfirm_by(String confirm_by) {
		this.confirm_by = confirm_by;
	}

	public String getConfirm_time() {
		return confirm_time;
	}

	public void setConfirm_time(String confirm_time) {
		this.confirm_time = confirm_time;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
