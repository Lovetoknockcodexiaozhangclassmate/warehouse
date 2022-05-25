package com.zhang.warehouse.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.zhang.warehouse.WarehouseApplication;
import com.zhang.warehouse.entity.UserSheetEntity;
import com.zhang.warehouse.service.UserSheetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张某
 */
public class ImportListener extends AnalysisEventListener<UserSheetEntity> {
    /**
     * 保存数据
     */
    private List<UserSheetEntity> list = new ArrayList<>();
    /**
     * 保存错误信息
     */
    private static List<String> error = new ArrayList<>();
    /**
     * 多少行
     */
    private int i = 1;
    private final UserSheetService userSheetService;
    /**
     * 用于判断有没有错误数据，只要有错误数据就全部不插入
     */
    Boolean bool = false;

    protected static final Logger logger = LoggerFactory.getLogger(WarehouseApplication.class);
    /**
     * 有参构造
     * @param userSheetService : 把业务层的类装进来
     */
    public ImportListener(UserSheetService userSheetService){
        this.userSheetService = userSheetService;
    }
    /**
     * 该方法每一条数据解析都会来调用
     *
     * @param analysisContext
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param userSheetEntity :
     */
    @Override
    public void invoke(UserSheetEntity userSheetEntity, AnalysisContext analysisContext) {
        logger.info("遍历表格中的数据是否正确");
        String userName = "^[A-Za-z0-9]+$";
        String password = "^[A-Za-z0-9]{6,16}$";
        List<UserSheetEntity> account = userSheetService.find();
        boolean boole = false;
        for (UserSheetEntity username:account) {
            if (userSheetEntity.getUserName().equalsIgnoreCase(username.getUserName())) {
                boole = true;
                break;
            }
        }
        if (boole){
            error.add("第"+i+"行账号已存在");
            bool = true;
        }
        if ("".equals(userSheetEntity.getUserName()) || userSheetEntity.getUserName() == null){
            error.add("第"+i+"行账号不能为空");
            bool = true;
        }
        if (!userSheetEntity.getUserName().matches(userName)){
            error.add("第"+i+"行账号只能为数字和字母");
            bool = true;
        }
        if ("".equals(userSheetEntity.getUserPassword()) || userSheetEntity.getUserPassword() == null){
            error.add("第"+i+"行密码不能为空");
            bool = true;
        }
        if (!userSheetEntity.getUserPassword().matches(password)){
            error.add("第"+i+"密码只能为数字和字母并且长度为6-16");
            bool = true;
        }
        list.add(userSheetEntity);
        i++;
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        //saveData();
        // 存储完成清理 list
        //list.clear();
    }

    public static List<String> getError(){
        return error;
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param analysisContext :
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if(!bool){
            saveData();
            list.clear();
            logger.info("所有数据解析完成");
        }
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        if(exception.getMessage() == null){
            i++;
        }
        logger.info("解析失败，但是继续解析下一行:{}"+exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            logger.info("第" + excelDataConvertException.getRowIndex() + "行，第" + excelDataConvertException.getColumnIndex() + "列解析异常");
        }
    }

    private void saveData() {
        if (list.size() > 0) {
            logger.info(list.size() + "条数据，开始存储数据库！");
            for (UserSheetEntity tb : list) {
                userSheetService.save(tb);
            }
            logger.info("存储数据库成功！");
        }else{
            logger.info("没有数据！");
            error.add("表格中没有数据");
        }
    }
}

