/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import org.sql2o.Sql2o;

/**
 *
 * @author Tohar
 */
public class DB {
  public static Sql2o sql2o;
    
    static{
      sql2o = new Sql2o("jdbc:mysql://localhost/inventory obat","root","");
  }  
}
