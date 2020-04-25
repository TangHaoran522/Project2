 public static void readFileByBytes(String fileName) {  
        File file = new File(fileName);  
        InputStream in = null;  
        try {  
            System.out.println("let byte as unit to read file,reading one byte at one time ：");  
            // reading one byte at one time 
            in = new FileInputStream(file);  
            int tempbyte;  
            while ((tempbyte = in.read()) != -1) {  
                System.out.write(tempbyte);  
            }  
            in.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
            return;  
        }  
        try {  
            System.out.println("let byte as unit to read file,reading one byte at one time ：");  
            // reading one byte at one time 
            byte[] tempbytes = new byte[100];  
            int byteread = 0;  
            in = new FileInputStream(fileName);  
            ReadFromFile.showAvailableBytes(in);  
            // reading for number of byte in the group，byteread is the number of byte that one time read  
            while ((byteread = in.read(tempbytes)) != -1) {  
                System.out.write(tempbytes, 0, byteread);  
            }  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        } finally {  
            if (in != null) {  
                try {  
                    in.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }  
