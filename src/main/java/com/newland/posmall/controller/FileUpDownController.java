package com.newland.posmall.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.FileUpDownService;
import org.ohuyo.rapid.base.service.TfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
public class FileUpDownController {
	@Autowired
	private FileUpDownService fileUpDownService;
	@Autowired
	private TfileService fileService;

	@RequestMapping("/file/download.do")
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.fileUpDownService.download(request, response);
	}

	@RequestMapping("/file/upload.do")
	public String upload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Tfile> map  = this.fileUpDownService.upload(request, response, null, null, -1);
		Tfile file = map.get("filename");
		System.err.println(file.getName());
		return "/file/upload";
	}

	@RequestMapping("/file/toupload.do")
	public String toUpload() {
		return "/file/upload";
	}

	@RequestMapping("/file/todown.do")
	public String toDownalod(Model model){
		List<Tfile> list = fileService.getAll();
		model.addAttribute("files", list);
		return "/file/download";
	}
}
