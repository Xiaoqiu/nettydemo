/**
 * Copyright (C): 恒大集团版权所有 Evergrande Group
 * FileName: TestNetty
 * Author:   huangxiaoqiu
 * Date:     2018-11-07 11:39
 * Description:
 */
/**
 * @author kate
 * @create 2018/11/7
 * @since 1.0.0
 */

import org.junit.Test;


import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author huangxiaoqiu
 * @since 1.0.0
 */

public class TestNetty {
   private  static final  Logger log = Logger.getLogger("lavasoft");
  @Test
  public void test(){
    Logger log = Logger.getLogger("lavasoft");
    log.setLevel(Level.INFO);
    Logger log1 = Logger.getLogger("lavasoft");
    System.out.println(log==log1);     //true
    Logger log2 = Logger.getLogger("lavasoft.blog");
    log2.setLevel(Level.WARNING);

    log.info("aaa");
    log2.info("bbb");
    log2.fine("fine");
  }

  @Test
  public void test2 (){
    int init = 60000;
    int total = 200000;
    int add = 16000;
    int count = 0;
    int sum = init;
    for (int i = 3 ; i < 13; i ++) {


      count ++;
      sum = sum + add;
      System.out.println( i + "月 : " + sum );
      if ( i == 9 ) {
        sum = sum - 36900;
        System.out.println( "---->" + i + "月 : " + sum );
      }
    }

    int rent = 2660 * 14;
    int oldRent = 36900;
    System.out.println( "rent : "  + rent);
  }


}