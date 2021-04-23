package Database;

import java.io.*;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
public class Txt {//��ȡȫ�����ݵ�֧�ֶȣ�д��hash_1�У���web_6_exp3��ȡ��д��mongo
	//count Ϊ��������
	public HashMap hash[] = new HashMap[8];
	
	public HashMap readFileByLines(String fileName, int count) {//��ȡÿ�����ݵ�֧�ֶȣ�����hash
		File file = new File(fileName);
		BufferedReader reader = null;
		HashMap hm = new HashMap();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				String name = tempString.split("\t")[0];
				if(name.equals("null")) {
					continue;
				}
				int all = Integer.parseInt(tempString.split("\t")[1]);
				
				String percent = "0";
				double per = (double)all/count;
				NumberFormat nf = NumberFormat.getPercentInstance(); 
				nf.setMinimumFractionDigits(2);//���ñ���С��λ 
				nf.setRoundingMode(RoundingMode.HALF_UP); //��������ģʽ 
				percent = nf.format(per); 
				
				hm.put(name,percent);
				
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return hm;
	}
	public static List<String> getAllFile(String directoryPath) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            list.add(file.getAbsolutePath());
        }
        return list;
    }
	public Txt() {//��ȡ�ļ����ж�Ӧ���ı�����
		int count = 100000000;
		String path= "E:\\github-����\\����ѧ��\\web���ݹ���ʵ����\\src\\result\\1";
		int c_count = 0;
		List<String> l = getAllFile(path);
		for(String one:l) {
			hash[c_count] = new HashMap();
			HashMap hm = readFileByLines(one, count);
			Set keyset = hm.keySet();
    		for(Object o:keyset){
    			hash[c_count].put(o,hm.get(o));
    		}
    		c_count++;
		}
	}
}
