package com.pknu.bbs.util;

import java.io.File;
import java.util.UUID;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Named
public class FileSaveHelper{
	@Autowired
	String saveDir;
	public String save(MultipartFile fname){
		
		//파일을 업로드 시키면 내 컴퓨터에 저장된다.
//		String saveDir="d:/upload/";
		String storedFname=UUID.randomUUID().toString()+"_"+fname.getOriginalFilename();
		//밑에 애들이 한라인으로 끝난다.
		try{
		fname.transferTo(new File(saveDir+storedFname));
		}catch(Exception e){
			e.printStackTrace();
		}
		//		File file = new File(saveDir+storedFname);
//		System.out.println(file.getAbsolutePath());
//		FileOutputStream os = null;
//		InputStream is = fname.getInputStream();
//		
//		os = new FileOutputStream(file);
//		int temp = -1;
//		while((temp=is.read())!=-1){
//			os.write(temp);
//		}
//		is.close();
//		os.close();
//	
		return storedFname;
	}
	
}
