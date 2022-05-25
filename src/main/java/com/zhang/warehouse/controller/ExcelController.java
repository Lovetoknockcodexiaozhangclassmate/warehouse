package com.zhang.warehouse.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.zhang.warehouse.WarehouseApplication;
import com.zhang.warehouse.code.ResponseData;
import com.zhang.warehouse.entity.UserSheetEntity;
import com.zhang.warehouse.listener.ImportListener;
import com.zhang.warehouse.service.impl.UserSheetServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 1
 */
@RestController
@RequestMapping("/excel")
public class ExcelController {
    protected static final Logger logger = LoggerFactory.getLogger(WarehouseApplication.class);

    private final UserSheetServiceImpl userSheetService;
    @Autowired
    public ExcelController(UserSheetServiceImpl userSheetService) {
        this.userSheetService = userSheetService;
    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws IOException {
        logger.info("导出方法");
        //把数据查询出来
        List<UserSheetEntity> userList = userSheetService.find();
        //导出操作
        logger.info(userList.toString());
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("账号信息表", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserSheetEntity.class)
                .sheet("用户信息")
                // 设置字段宽度为自动调整，不太精确
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(userList);

    }

    @RequestMapping(value="/import", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData onlineupload(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("进入导入方法");
       //进来这方法先清空list中的错误提示；
        ImportListener.getError().clear();
        EasyExcel.read(file.getInputStream(), UserSheetEntity.class, new ImportListener(userSheetService)).sheet().doRead();
        //返回的是表格中错误的数据
        if (null == ImportListener.getError() || ImportListener.getError().size() == 0){
            return ResponseData.success("上传成功", null);
        }else{
            return ResponseData.error("上传失败",ImportListener.getError());
        }
    }
}
