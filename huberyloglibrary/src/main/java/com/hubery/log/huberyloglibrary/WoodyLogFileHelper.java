package com.hubery.log.huberyloglibrary;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public class WoodyLogFileHelper {

    private static final String TAG = "WoodyLogFileHelper";

    private static WoodyLogFileHelper mInstance;

    public static final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    private static final String _regexp = "[0-9]{8}\\.log$";

    private WoodyLogFileHelper() {

    }

    private WoodyLogFileHelper(Context context) {

    }

    public synchronized static WoodyLogFileHelper getInstance() {
        if (mInstance == null) {
            mInstance = new WoodyLogFileHelper();
        }
        return mInstance;
    }


    public void saveLogFile(String strContext) {

        if (TextUtils.isEmpty(LogConfig.getLogPaht())) {
            return;
        } else {
            File mDir = new File(LogConfig.getLogPaht());
            if (!mDir.exists()) {
                mDir.mkdir();
            }
        }

        FileWriter fileWrite = null;
        File file = null;
        try {

            String currentDate = mSimpleDateFormat.format(new java.util.Date());

            file = new File(LogConfig.getLogPaht() + currentDate + LogConfig.LOG_SUFFIX);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() >= LogConfig.LOG_MAXSIZE) {
                if (null != copyFile(LogConfig.getLogPaht() + currentDate + LogConfig.LOG_SUFFIX)) {
                    file = new File(LogConfig.getLogPaht() + currentDate + LogConfig.LOG_SUFFIX);
                }
            }
            fileWrite = new FileWriter(file, true);
            fileWrite.write(strContext);
            fileWrite.write("\n");
            fileWrite.flush();
            fileWrite.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        } finally {
            try {
                if (fileWrite != null) {
                    fileWrite.close();
                }
            } catch (IOException e) {
            }

        }
    }


    public File copyFile(String srcFilePath) throws Exception {
        RandomAccessFile srcFile = new RandomAccessFile(srcFilePath, "rw");
        srcFile.seek(LogConfig.LOG_MAXSIZE / 2);
        File destFile = new File(srcFilePath + "_temp");
        int byteread = 0;
        OutputStream out = null;
        try {
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            while ((byteread = srcFile.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            out.flush();
            if (srcFile != null) {
                srcFile.close();
            }
            if (out != null) {
                out.close();
            }
            File mSrcFile = new File(srcFilePath);
            mSrcFile.delete();
            destFile.renameTo(new File(srcFilePath));

            return destFile;
        } catch (Exception e) {
            Log.v(TAG, e.toString());
        }

        return null;
    }


    public File getProcessLogFile(String fileName) {
        File file = new File(LogConfig.getLogPaht() + fileName + LogConfig.LOG_SUFFIX);
        if (null != file && file.isFile() && file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 获取已打印日志文件数
     *
     * @return
     */
    public File[] getLocalLogFileNums() {
        File file = null;
        try {
            file = new File(LogConfig.getLogPaht());
            if (null != file && file.isDirectory() && file.exists()) {
                File[] filelist = file.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        try {
                            Pattern pattern = Pattern.compile(_regexp);
                            Matcher match = pattern.matcher(pathname.getName());
                            if (pathname.getName().endsWith(LogConfig.LOG_SUFFIX) && match.matches()) {
                                return true;
                            } else {
                                return false;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                });
                Arrays.sort(filelist);
                return filelist;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 获取已保存文件名称
     *
     * @return
     */
    public String[] getLocalLogNames() {
        try {
            File file = new File(LogConfig.getLogPaht());
            if (null != file && file.isDirectory() && file.exists()) {
                String[] fileNames = file.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        try {
                            Pattern pattern = Pattern.compile(_regexp);
                            Matcher match = pattern.matcher(filename);
                            if (filename.endsWith(LogConfig.LOG_SUFFIX) && match.matches()) {
                                return true;
                            } else {
                                return false;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                });
                Arrays.sort(fileNames);
                return fileNames;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void deleteProcessLogFile(File file) {
        if (null != file && file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 获取文件大小
     *
     * @param mList
     * @return
     */
    public long getLogFileSize(List<String> mList) {
        long allSize = 0;
        try {
            File file = new File(LogConfig.getLogPaht());
            if (null != file && file.isDirectory() && file.exists()) {
                for (String fileName : mList) {
                    if (!TextUtils.isEmpty(fileName)) {
                        File mFile = new File(LogConfig.getLogPaht() + fileName + LogConfig.LOG_SUFFIX);
                        if (null != mFile && mFile.exists()) {
                            allSize += mFile.length();
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return allSize;
    }

    /**
     * 获取当天文件大小
     *
     * @return
     */
    public long getTodayFileSize() {
        String currentDate = mSimpleDateFormat.format(new java.util.Date());
        List<String> mList = new ArrayList<String>();
        mList.add(currentDate);
        return getLogFileSize(mList);
    }

}
