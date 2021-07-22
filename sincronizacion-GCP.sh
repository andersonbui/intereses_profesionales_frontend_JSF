rsync --progress -ra -e "ssh -i /home/debian/.ssh/abuitron" /home/debian/Documentos/proyectos_ingenisoft/encuesta_Intereses_profesionales/intereses_profesionales_frontend_JSF/target/intereses_profesionales-1.0-SNAPSHOT/* abuitron@35.243.252.249:/home/abuitron/intereses_profesionales-1.0-SNAPSHOT


#extraer bd
# ssh -i /home/debian/.ssh/abuitron  abuitron@35.243.252.249 mysqldump -u  interpro -p'1nterpr0' interpro > interpro-01-10-19.sql
