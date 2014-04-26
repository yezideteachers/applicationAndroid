<?php

   /* Si la requête a des paramètres */
   
   if (isset($_POST['tag']) && $_POST['tag'] != '') 
   {
      
      /* Configuration de connexion à la base de données */
      

      define("DB_HOST", "localhost");
      /* mettre pour le nom de l'utilisateur et le mot de passe de la base de donnes a default c'est root */
      define("DB_USER", "root");
      define("DB_PASSWORD", "root");
      define("DB_DATABASE", "android_api");

      /* Connexion à MySQL */
      
      if(mysql_connect(DB_HOST, DB_USER, DB_PASSWORD))
      {
         // Sélection de la BDD
         mysql_select_db(DB_DATABASE);
      }

      /* Récupération du tag de la requête */

      $tag = $_POST['tag'];

      /* Initialisation du tableau de réponse */
      
      $response = array("tag" => $tag, "success" => 0, "error" => 0);

      
      /* Si c'est une demande de type 'login' */
      
      if ($tag == 'login') 
      {
         $email = $_POST['email'];
         $password = $_POST['password'];

         
         /* Teste si l'utilisateur existe */
        
     	 $user = mysql_query("SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
         $no_of_rows = mysql_num_rows($user);
         if ($no_of_rows > 0) 
         {
            $user_details = mysql_fetch_array($user);      
            
            
            /* Teste si le mot de passe est correct */
            
            if (md5($password) == $user['password']) 
            {
                $user = 1;
            }
         } 
         else 
         {
           
            /* Utilisateur non trouvé */
            
            $user = 0;
         }
         /* Utilisateur trouvé */
         
         if ($user != false) 
         {
            $response["success"] = 1;
            $response["uid"] = $user_details["unique_id"];
            $response["name"] = $user_details["name"];
            $response["email"] = $user_details["email"];
            $response["created_at"] = $user_details["created_at"];
            $response["updated_at"] = $user_details["updated_at"];
            echo json_encode($response);
         } 
         
         /* Utilisateur non trouvé */
         
         else 
         {
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password !";
            echo json_encode($response);
         }
      } 

      
      /* Si c'est une demande de type 'inscription' */
      
      else if ($tag == 'sinscrire') 
      {
         /** Request type is Register new user  */
         $name = $_POST['name'];
         $email = $_POST['email'];
         $password = md5($_POST['password']);

         
         /* Teste si l'utilisateur existe déjà */
         

         $user = mysql_query("SELECT email from users WHERE email = '$email'");
         $no_of_rows = mysql_num_rows($user);

         if ($no_of_rows > 0) 
         {
            // L'utilisateur existe déjà 
            $user = 1;
         } 
         else 
         {
            // L'utilisateur n'existe pas 
            $user = 0;
         }

        
         /* Si l'utilisateur existe et qu'aucun champ n'est vide */
        
         if ($user || $name == NULL || $email == NULL || $password == NULL) 
         {
            // L'utilisateur existe déjà
            $response["error"] = 2;
            $response["error_msg"] = "User already existed or empty field";
            echo json_encode($response);
         } 

         
         /* Alors l'utilisateur n'existe pas on l'ajoute à la BDD */
         
         else 
         {            
            $uuid = uniqid('', true);            
            $result = mysql_query("INSERT INTO users (unique_id, name, email, password, created_at) VALUES ('$uuid', '$name', '$email', '$password', NOW());");

           
            /* Teste si l'ajout s'est correctement effectué */
            
            if ($result) 
            {
               $uid = mysql_insert_id(); // last inserted id
               $result = mysql_query("SELECT * FROM users WHERE uid = $uid");
               $user_details = mysql_fetch_array($result);
               
               /****************/
               /* Ajout réussi */
               /****************/

               $response["success"] = 1;
               $response["uid"] = $user_details["unique_id"];
               $response["user"]["name"] = $user_details["name"];
               $response["user"]["email"] = $user_details["email"];
               $response["user"]["created_at"] = $user_details["created_at"];
               $response["user"]["updated_at"] = $user_details["updated_at"];
               
               echo json_encode($response);

            } 
            else 
            {
               
                /* Ajout échoué */
                
                $response["error"] = 1;
                $response["error_msg"] = "Error occured in Registration";
                echo json_encode($response);
            }
         }
      } 
      else 
      {
         echo "Invalid Request";
      }

   } 
   
   /* Accès refusé */
      
   else 
   {
      //echo "bonjour";
      echo "Access Denied";
   }
?>
