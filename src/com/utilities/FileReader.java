package com.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

import com.yantra.yfc.dom.YFCDocument;
import com.yantra.yfc.dom.YFCElement;
import com.yantra.yfc.util.YFCCommon;

public abstract class FileReader {
	protected String filePath = null;
	private BufferedReader in = null;
	private YFCElement fileDoc = null;
	
	protected abstract boolean fileInstructions (String sPath) throws Exception;
	
	public FileReader(String sPath){
		filePath = sPath;
	}

	public BufferedReader getContents() throws Exception {
		if(!YFCCommon.isVoid(in)){
			in.reset();
		} else {
			File file = getFile(filePath);
			if(!YFCCommon.isVoid(file) && file.isFile()){
				in = new BufferedReader(new java.io.FileReader(file));
			}
		}
		return in;
	}
	
	public YFCElement getXml(){
		if(!YFCCommon.isVoid(fileDoc)){
			return fileDoc;
		}
		fileDoc = getXml(filePath);
		return fileDoc;
	}
	
	public static YFCElement getXml(String sPath){
		File temp = new File(sPath);
		if(temp.exists()){
			return YFCDocument.getDocumentForXMLFile(sPath).getDocumentElement();
		}
		return null;
	}
	
	public static File getFile(String sPath){
		File file = new File(sPath);
		if(file.exists()){
			return file;
		}
		return null;
	}
	
	public void parseDirectory(String sPath, String fileExtension){
		File dir = new File(sPath);
		if(dir.isDirectory()){
			String s[] = dir.list();
			for(int i = 0; i < s.length; i++){
				File f = new File(sPath + "/" + s[i]);
				if(f.isDirectory()){
					parseDirectory(sPath + "/" + s[i], fileExtension);
				} else if(s[i].indexOf(fileExtension) > -1){
					try{
						if(fileInstructions(sPath + "/" + s[i])){
							break;
						}
					}catch(Exception e){
						e.printStackTrace();
						System.out.println(sPath + "/" + s[i]);
					}
				}				
			}
		}
	}
	
	public void parseDirectory(String sPath, String[] fileExtension){
		File dir = new File(sPath);
		if(dir.isDirectory()){
			String s[] = dir.list();
			for(int i = 0; i < s.length; i++){
				File f = new File(sPath + "/" + s[i]);
				if(f.isDirectory()){
					parseDirectory(sPath + "/" + s[i], fileExtension);
				} else {
					for (String sFileExtension : fileExtension){
						if(s[i].indexOf(sFileExtension) > -1){
							try{
								if(fileInstructions(sPath + "/" + s[i])){
									break;
								}
							}catch(Exception e){
								e.printStackTrace();
								System.out.println(sPath + "/" + s[i]);
							}
						}					
					}
				
				}				
			}
		}
	}
	
	public void writeXML(String sFilePath, String sFileName, YFCDocument output){
		validatePath(sFilePath);
		FileWriter fout;
		try{
			deleteExistingFile(sFilePath + "/" + sFileName);
			char buffer[] = new char[output.toString().length()];
			output.toString().getChars(0,output.toString().length(), buffer, 0);
			fout = new FileWriter(sFilePath + "/" + sFileName);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.close();
		}catch(Exception e){
			System.out.println("Error Opening File");
		}
		//System.out.println("Writing File: " + sFilePath + "/" + sFileName);
	}
	
	public static void writeXML(String sPath, YFCDocument output){
		FileWriter fout;
		try{
			deleteExistingFile(sPath);
			char buffer[] = new char[output.toString().length()];
			output.toString().getChars(0,output.toString().length(), buffer, 0);
			fout = new FileWriter(sPath);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.close();
		}catch(Exception e){
			System.out.println("Error Opening File");
		}
		//System.out.println("Writing File: " + sFilePath + "/" + sFileName);
	}
	
	public void updateFile(YFCDocument input){
		FileWriter fout;
		try{
			deleteExistingFile(filePath);
			char buffer[] = new char[input.toString().length()];
			input.toString().getChars(0,input.toString().length(), buffer, 0);
			fout = new FileWriter(filePath);
			for(int i=0; i<buffer.length; i ++){
				fout.write(buffer[i]);
			}
			fout.close();
			System.out.println("Updating File: " + filePath);
		}catch(Exception e){
			System.out.println("Error Opening File");
		}
		
	}
	
	protected void validatePath(String sFilePath){
		File temp = new File(sFilePath);
		temp.mkdirs();
	}
	
	private static void deleteExistingFile(String sFile){
		File temp = new File(sFile);
		if(temp.exists()){
			temp.delete();
		}
	}
}
