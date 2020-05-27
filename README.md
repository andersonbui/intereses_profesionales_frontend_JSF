# intereses_profesionales_frontend_JSF



configuración para re-direccionamiento con apache

Activar los modulos

	$ sudo a2enmod rewrite
	$ sudo a2enmod ssl
	$ sudo a2enmod proxy_http

Reiniciar Apache2 para que tenga en cuenta al módulo recién activado.

	$ sudo service apache2 restart

Ejemplo:

```bash
<virtualhost *:80>
        ServerAdmin andersonb@hotmail.es
        ServerName suideal.co
        ServerAlias www.suideal.co
        DocumentRoot /var/www/cc/htl
        ErrorLog ${APACHE_LOG_DIR}/error.log
        CustomLog ${APACHE_LOG_DIR}/access.log combined

        #redireccionar cualquier solicitud http a https
        RewriteEngine On
        RewriteCond 5{HTTPS} off
        RewriteRule (.*) https://%{SERVER_NAME}/$1 [R,L]
</virtualhost>

<VirtualHost *:443>
        SSLEngine on

        SSLCertificateFile /etc/ssl/certs/suideal.co.pem
        SSLCertificateKeyFile /etc/ssl/private/suideal.co.key
        #SSLCertificateChainFile /etc/ssl/private/suideal.co.pem

        #DocumentRoot /var/www/html/merchants_abuitron/public
        ServerName suideal.co
        #ServerAlias merchants-dev-abuitron.ringvoz.com

        # reverse proxy configuration
        <Location / >
                ProxyPass http://suideal.co:8080/
                ProxyPassReverse http://suideal.co:8080/
        </Location>     
</VirtualHost>
```

### instalacion manual de dependencias