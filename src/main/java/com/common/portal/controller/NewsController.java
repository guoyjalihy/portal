package com.common.portal.controller;

import com.common.portal.controller.vo.NewsVO;
import com.common.portal.service.NewsService;
import com.common.portal.util.PageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class NewsController {

	@Autowired
	private NewsService newsService;

	@RequestMapping("/newsManage_{pageCurrent}_{pageSize}_{pageCount}")
	public String newsManage(NewsVO news, @PathVariable Integer pageCurrent, @PathVariable Integer pageSize, @PathVariable Integer pageCount, Model model) {
		
		//判断
		if(pageSize == 0) pageSize = 10;
		if(pageCurrent == 0) pageCurrent = 1;
		int rows = newsService.count(news);
		if(pageCount == 0) pageCount = rows%pageSize == 0 ? (rows/pageSize) : (rows/pageSize) + 1;
		
		//查询
		news.setStart((pageCurrent - 1)*pageSize);
		news.setEnd(pageSize);
		List<NewsVO> newsList = newsService.list(news.getTitle(),news.getCategory());


		model.addAttribute("newsList", newsList);
		String pageHTML = PageUtil.getPageContent("newsManage_{pageCurrent}_{pageSize}_{pageCount}?title="+news.getTitle()+"&category="+news.getCategory(), pageCurrent, pageSize, pageCount);
		model.addAttribute("pageHTML",pageHTML);
		model.addAttribute("news",news);
		
		return "news/newsManage";
	}
	
	
	/**
	 * 文章新增、修改跳转
	 * @return
	 */
	@GetMapping("/newsEdit")
	public String newsEditGet() {
		return "news/newsEdit";
	}
	
	/**
	 * 文章新增、修改提交
	 * @param news
	 * @param imageFile
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/newsEdit")
	public String newsEditPost(NewsVO news, @RequestParam MultipartFile[] imageFile,HttpSession httpSession) {
		for (MultipartFile file : imageFile) {
			if (file.isEmpty()) {
				System.out.println("文件未上传");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Random random = new Random();
				Date date = new java.util.Date();
				String strDate = sdf.format(date);
				String fileName = strDate + "_" + random.nextInt(1000) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),file.getOriginalFilename().length());
				String realPath = httpSession.getServletContext().getRealPath("/userfiles");
				System.out.println("realPath : "+realPath);
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(),new File(realPath, fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		newsService.saveOrUpdate(news);
		return "redirect:newsManage_0_0_0";
	}
	
}
