/**
 * 
 */
package com.tmusoft.payment.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

/**
 * @author wumeng
 *
 */
public class CreatePngFile {
	
	
	//生成二维码
    public static void createPngFile(String path,String fileName,String content) throws Exception{
    	
    	
    	
		MultiFormatWriter multiFormatWrite = new MultiFormatWriter();
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = multiFormatWrite.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
		File file1 = new File(path);
		if (!file1.isDirectory()) {
			file1.mkdirs();
		}
		File file = new File(path, fileName + ".png");
		if(!file.exists()){
			MartixToImageWriter.writeToFile(bitMatrix, "png", file);
		}
		
	}

}
