package com.pknu.bbs.service;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.pknu.bbs.dao.BBSDao;
import com.pknu.bbs.dto.BBSDto;
import com.pknu.bbs.dto.FileDto;
import com.pknu.bbs.dto.UpdateDto;
import com.pknu.bbs.util.FileSaveHelper;
import com.pknu.bbs.util.LoginStatus;
import com.pknu.bbs.util.Page;

@Service
//빈에 직접 등록하면 모든 메소드에 다 트랜잭션 처리가능

public class BBSServiceImpl implements BBSService {

	@Inject
	BBSDao bbsDao;
	
	@Resource
	Page page;
	List<BBSDto> articleList;
	
	@Inject
	FileSaveHelper fileSaveHelper;
	
	@Resource(name="saveDir")
	String saveDir;
	String originFname;
	String storedFname;
	
	BBSDto article;
	
	ModelAndView mav= null;
	
	List<FileDto> fileList;
	
	@Transactional
	public ModelAndView list(int pageNum){
		mav = new ModelAndView();
		
//		articleList = new ArrayList<>();
		int totalCount=0;
		int pageSize=10; // 한 페이지에 보여줄 게시글의 갯수
		int pageBlock=10; // 한 블럭당 보여줄 페이지 갯수. 게시판 하단에 숫자
		
		totalCount = bbsDao.getArticleCount();
		page.paging(pageNum, totalCount,pageSize,pageBlock);
		
		HashMap<String, Integer> hm = new HashMap<>();
		hm.put("startRow", page.getStartRow());
		hm.put("endRow", page.getEndRow());
		articleList= bbsDao.getArticles(hm);
		
		mav.addObject("totalCount", totalCount);
		mav.addObject("articleList", articleList);
		mav.addObject("pageNum", pageNum);
		mav.addObject("pageCode", page.getSb().toString());
		mav.setViewName("list");
		
		return mav;
	}
	
	@Transactional
	public ModelAndView masterList(int pageNum){
		mav = new ModelAndView();
		
//		articleList = new ArrayList<>();
		int totalCount=0;
		int pageSize=10; // 한 페이지에 보여줄 게시글의 갯수
		int pageBlock=10; // 한 블럭당 보여줄 페이지 갯수. 게시판 하단에 숫자
		
		totalCount = bbsDao.getArticleCount();
		page.paging(pageNum, totalCount,pageSize,pageBlock);
		
		HashMap<String, Integer> hm = new HashMap<>();
		hm.put("startRow", page.getStartRow());
		hm.put("endRow", page.getEndRow());
		articleList= bbsDao.getMasterArticles(hm);
		
		mav.addObject("totalCount", totalCount);
		mav.addObject("masterArticleList", articleList);
		mav.addObject("pageNum", pageNum);
		mav.addObject("pageCode", page.getSb().toString());
		mav.setViewName("master");
		
		return mav;
	}

	@Override
	public int loginCheck(String id, String pass) {
		String dbPass=bbsDao.loginCheck(id);
		int loginStatus = 0;
		//하나의 컬럼의 값을 가져올때는 queryForList를 쓴다. 제일마지막은 넘어올 값의 타입을 적어주면 된
		
			if (dbPass!=null) {
				if (pass.equals(dbPass)) {
					// LoginStatus 를 인터페이스로 만든다.
					loginStatus = LoginStatus.OK;
				} else {
					loginStatus = LoginStatus.PASS_FAIL;
				}
			} else {
				loginStatus = LoginStatus.NOT_MEMBER;
			}
		return loginStatus;
	}
	

//	동기화로 인한 성능 저하가 있을 수 있으므로 밑에 하나 새로 만듦
//	@Override
//	public synchronized void insertArticle(ContentDto article, MultipartFile fname) {
//		if(fname.isEmpty()){
//			bbsDao.insertArticle(article);
//		}else{
//			storedFname=fileSaveHelper.save(fname);
//
//			article.setOriginFname(fname.getOriginalFilename());
//			article.setStoredFname(storedFname);
//			article.setFileLength(fname.getSize());
//			article.setFileStatus(1);
//			bbsDao.insertArticle(article);
//			bbsDao.insertFile(article);
//		}
//		
//	}
	
	@Transactional
	@Override
	public void insertArticle(BBSDto article, MultipartHttpServletRequest mRequest) {
		List<MultipartFile> mfile = mRequest.getFiles("fname");
		
		//파일 업로드가 안된 글을 걸러준다~
		if(mfile.get(0).isEmpty()){
			bbsDao.insertArticle(article);
		}else{
//			storedFname=fileSaveHelper.save(mRequest);
//
//			article.setOriginFname(mRequest.getOriginalFilename());
//			article.setStoredFname(storedFname);
//			article.setFileLength(mRequest.getSize());
//			article.setFileStatus(1);
//			
//			int articleNum=  bbsDao.getNextArticleNum();
//			article.setArticleNum(articleNum);
//			bbsDao.insertArticle(article);
//			bbsDao.insertFile(article);
			
			int articleNum=bbsDao.getNextArticleNum();
			article.setArticleNum(articleNum);
			article.setFileStatus(1);
			bbsDao.insertArticle(article);
			commonFileUpload(mfile,articleNum);
	    }
	   
	    
	}
	
	
	//파일 업로드를 하기 위한 메소오드
	public void commonFileUpload(List<MultipartFile> mfile, int articleNum){
		FileDto fileDto = null;
		
		for(MultipartFile uploadFile : mfile){
			if(!uploadFile.isEmpty()){
				String storedFname = fileSaveHelper.save(uploadFile);
				
				fileDto = new FileDto();					
				fileDto.setOriginFname(uploadFile.getOriginalFilename());
				fileDto.setStoredFname(storedFname);
				fileDto.setFileLength(uploadFile.getSize()); 	
				fileDto.setArticleNum(articleNum); 	
	
				bbsDao.insertFile(fileDto);
			}
		}
	}

	@Transactional
	@Override
	public ModelAndView getArticle(int articleNum, int fileStatus) {
		mav = new ModelAndView();
		BBSDto article = null;
		fileList = null;
		
		bbsDao.getHit(articleNum);
		article = bbsDao.getArticle(articleNum);
		article.setCommentCount(bbsDao.getCommentCount(articleNum));
		
		//fileStatus가 1일때만 가져온다.
		if(fileStatus==1){
			fileList = bbsDao.getFiles(articleNum);
			mav.addObject("fileList", fileList);
		}
		
		mav.addObject("article", article);
		mav.setViewName("content");
		
		return mav;
	}
	
	@Transactional
	@Override
	public void deleteArticle(int articleNum) {
		//올렸던 파일도 같이 삭제
		deleteFile(articleNum);
		bbsDao.deleteFile(articleNum);
		bbsDao.deleteArticle(articleNum);
	}

	@Transactional
	@Override
	public void replyArticle(BBSDto article, MultipartHttpServletRequest mRequest) {
		
		List<MultipartFile> mfile = mRequest.getFiles("fname");
		if(mfile.get(0).isEmpty()){
			bbsDao.upPos(article);
			bbsDao.replyArticle(article);
		}else{
			int articleNum=bbsDao.getNextArticleNum();
			article.setArticleNum(articleNum);
			article.setFileStatus(1);
			bbsDao.replyArticle(article);
			commonFileUpload(mfile,articleNum);
		}
	}

	
	@Override
	public ModelAndView getUpdateArticle(int articleNum, int fileStatus) {
		mav = new ModelAndView();
		
		article=bbsDao.getUpdateArticle(articleNum);
		
		if(fileStatus == 1){
			fileList = bbsDao.getFiles(articleNum);
			mav.addObject("fileList", fileList);
		}
		
		mav.addObject("article", article);
		mav.setViewName("updateForm");
		return mav;
	}

	@Transactional
	@Override
	public ModelAndView updateArticle(MultipartHttpServletRequest mRequest,BBSDto article,UpdateDto updateDto) {
		mav= new ModelAndView();
		List<MultipartFile> mfile=mRequest.getFiles("fname"); 
//		새로운 파일이 업로드 없을경우
		if(mfile.get(0).isEmpty()){
//			기존에 업로드되어 있는 파일중 하나라도 삭제된경우
			if(updateDto.getFileNumList()!=null){						
				for(int fileNum : updateDto.getFileNumList()){
					updateSomeDelFile(fileNum);
				}			
				bbsDao.someDelFile(updateDto.getFileNumList());
				int fileListCount=
						bbsDao.getFileListCount(article.getArticleNum());
				if(fileListCount==0){
					article.setFileStatus(0);					
				}
			}
			bbsDao.updateArticle(article);
		}else{				
			if(updateDto.getFileNumList()!=null){				
				for(int fileNum : updateDto.getFileNumList()){
					updateSomeDelFile(fileNum);
				}			
				bbsDao.someDelFile(updateDto.getFileNumList());					
			}else{					
				article.setFileStatus(1);				
			}			
			commonFileUpload(mfile,article.getArticleNum());
			bbsDao.updateArticle(article);			
		}
		mav.addObject("articleNum", article.getArticleNum());
		mav.addObject("fileStatus", article.getFileStatus());
		mav.setViewName("redirect:/content.bbs");		
		return mav;
	}

//	@Override
//	public void updateFileArticle(ContentDto article, MultipartFile fname) {
//		
//		storedFname=fileSaveHelper.save(fname);			
//		article.setOriginFname(fname.getOriginalFilename());
//		article.setStoredFname(storedFname);
//		article.setFileLength(fname.getSize());
//		
//		if(article.getFileStatus()==0){
//			article.setFileStatus(1);
//			bbsDao.updateArticle(article);
//			bbsDao.insertFile(article);
//		}else{
//			/*파일이 들어있는 글은 파일 업로드도 수정이 되면서 
//			 이전 파일 D:\ upload에 있는 파일도 같이 수정되야 해서 이전 파일을 지워준다.*/
//			this.delFile(article.getArticleNum());
//			bbsDao.updateArticle(article);
//			bbsDao.updateFileArticle(article);
//		}
//	}
	
	public void updateSomeDelFile(int fileNum){
		String storedFname=bbsDao.updateSomeDelFile(fileNum);
		if(!storedFname.isEmpty()){			
			File file = new File(saveDir+storedFname);
			if(file.exists()){
				file.delete();
			}			
		}
	}
	
	public void deleteFile(int articleNum){
		List<String> delFileList=bbsDao.deleteFile(articleNum);
		if(!delFileList.isEmpty()){
			for(String storedFname : delFileList){
				File file = new File(saveDir+storedFname);
				if(file.exists()){
					file.delete();
				}
			}
		}
	}

	@Override
	public FileSystemResource download(HttpServletResponse res,String originFname, String storedFname) {
		res.setContentType("application/download");
		try{
			originFname = URLEncoder.encode(originFname,"UTF-8").replace("+","%20");
		}catch(Exception e){
			e.printStackTrace();
		}
		res.setHeader("Content-Disposition", "attachment;" + "filename=\""+originFname +"\";");
		FileSystemResource fsr = new FileSystemResource(saveDir + storedFname);
		
		return fsr;
	}
	
	

}
