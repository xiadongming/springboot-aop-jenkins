package com.example.demo.web;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class WebController {
	@RestController
	@RequestMapping("v1/uploadDownload")
	public class UploadDownloadController {
		@Value("${uploadDir}")
		private String uploadDir;

		@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
		public String uploadImage(@RequestParam(value = "file") MultipartFile file) throws RuntimeException {
			if (file.isEmpty()) {
				return "文件不能为空";
			}
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 文件上传后的路径
			String filePath = uploadDir;
			// 解决中文问题，liunx下中文路径，图片显示问题
			// fileName = UUID.randomUUID() + suffixName;
			File dest = new File(filePath + fileName);
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			try {
				file.transferTo(dest);
				return fileName;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "文件上传失败";
		}
	}
}