package com.allmsi.plugin.open.service;

import java.util.List;

public interface OpenService {

	Object select(String token, String code);

	boolean checkToken(String token);
	
	Object insert (String objectId,List<String> receiveIds,String cUser,String type);
	
	Object insert (String objectId,List<String> receiveIds,String cUser,String type,int tCount);

}
