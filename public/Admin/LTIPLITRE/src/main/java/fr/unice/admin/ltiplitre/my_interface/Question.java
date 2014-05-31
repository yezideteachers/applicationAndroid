package fr.unice.admin.ltiplitre.my_interface;

/**
 * Created by yezide on 31/05/2014.
 */
public interface Question {


    /** cette fonction permet de recuperer une question de la base de donnée,
     * renvoie true en cas de succes false sinon
     *
     * @return boolean
     */
    boolean recoverOneQuestion();


    /** cette fonction permet de recuperer toute les question de la base de donnée,
     * renvoie true en cas de succes false sinon
     *
     * @return boolean
     */
   String recoverAllQuestion();



}
