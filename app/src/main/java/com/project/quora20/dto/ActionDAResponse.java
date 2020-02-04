package com.project.quora20.dto;

import com.google.gson.annotations.SerializedName;

public class ActionDAResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("errorMessage")
	private String errorMessage;

	@SerializedName("content")
	private String content;

	@SerializedName("status")
	private String status;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setErrorMessage(String errorMessage){
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage(){
		return errorMessage;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ActionDAResponse{" + 
			"success = '" + success + '\'' + 
			",errorMessage = '" + errorMessage + '\'' + 
			",content = '" + content + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}