//package com.example.demo.controller;
//
//import com.example.demo.IStatusMessage;
//import com.example.demo.ServiceException;
//import com.example.demo.entity.Result;
//import com.example.demo.service.FileUpAndDownService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/upload")
//public class FileUploadController {
//
//
//    @Autowired
//    private FileUpAndDownService fileUpAndDownService;
//
//    @RequestMapping(value = "/setFileUpload", method = RequestMethod.POST)
//    @ResponseBody
//    public Result setFileUpload(@RequestParam(value = "file", required = false) MultipartFile file) {
//        Result result = new Result();
//        try {
//            Map<String, Object> resultMap = upload(file);
//            if (!IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(resultMap.get("result"))) {
//                result.setCode(400);
//                result.setMsg((String) resultMap.get("msg"));
//                return result;
//            }
//            result.setData(resultMap);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            result.setCode(401);
//            result.setMsg("图片上传异常");
//        }
//        return result;
//    }
//
//    private Map<String, Object> upload(MultipartFile file) throws ServiceException {
//        Map<String, Object> returnMap = new HashMap<>();
//        try {
//            if (!file.isEmpty()) {
//                Map<String, Object> picMap = fileUpAndDownService.uploadPicture(file);
//                if (IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(picMap.get("result"))) {
//                    return picMap;
//                } else {
//                    returnMap.put("result", 402);
//                    returnMap.put("msg", picMap.get("result"));
//                }
//            } else {
//                returnMap.put("result", 402);
//                returnMap.put("msg", "上传信息为空");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnMap;
//    }
//}
//
