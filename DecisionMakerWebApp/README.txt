Exemplo OAuth:
http://ocpsoft.org/java/setting-up-google-oauth2-with-java/

JPA no Tomcat:
Os JAR's têm de estar nas libs partilhadas do Tomcat
	Acrescentar/alterar a linha abaixo ao ficheiro "catalina.properties"
		shared.loader=${catalina.home}/shared/lib/*.jar
ou
	no directório "WEB-INF/lib" da web application

Exemplo i18n:	
http://stackoverflow.com/questions/4276061/how-to-internationalize-a-java-web-application

Exemplo Radio Buttons:
http://www.dummies.com/how-to/content/how-to-create-a-radio-button-in-an-html5-form.html

Ver
http://aws.amazon.com/ec2/pricing/
https://cloud.google.com/pricing/compute-engine

TODO
- Refactorizar a página da afluência para ter 3 tabs: Resumo; Unidades orgânica; Eleitores




# ===============================================
# Instalação Google Cloud Compute
# ===============================================

# Pacotes
sudo apt-get install openjdk-7-jre-headless
sudo apt-get install tomcat7
sudo apt-get install mysql-server
sudo apt-get install zip unzip vim mc

# Definir DIR GIT
export MY_GIT = ~/work/fndni-decision-maker-master/

# Copiar libs JPA para o shared lib do Tomcat
sudo cp $MY_GIT/libs/jpa/* /var/lib/tomcat7/shared/
sudo chown tomcat7:tomcat7 /var/lib/tomcat7/shared/*

# Inicializar BD
mysql -u root -p decision_maker < $MY_GIT/DecisionMakerJPA/sql/createDatabase.sql
mysql -u root -p decision_maker < $MY_GIT/DecisionMakerJPA/sql/createDDL.sql 

# Configurar Web App no Tomcat
sudo ln -s $MY_GIT/DecisionMakerWebApp/WebContent /var/lib/tomcat7/webapps/DecisionMakerWebApp

# Configurar Web App no Tomcat para o URL definitivo, para não precisar do sufixo DecisionMakerWebApp
sudo mv /var/lib/tomcat7/webapps/ROOT/ ~/work/
cd /var/lib/tomcat7/webapps
sudo mv DecisionMakerWebApp ROOT

# Preparar ambiente para actualizar o "persistence.xml" 
mkdir ~/work
cd ~/work
cp $MY_GIT/DecisionMakerWebApp/WebContent/WEB-INF/lib/dmJPA.jar .
mkdir dmJPA
cp dmJPA.jar dmJPA
cd dmJPA
unzip dmJPA.jar

# Actualizar "persistence.xml", para colocar as credenciais de acesso à BD
vi META-INF/persistence.xml
rm dmJPA.jar 
zip -r dmJPA.jar *
cp dmJPA.jar $MY_GIT/DecisionMakerWebApp/WebContent/WEB-INF/lib/

# Configurar as propriedades de autenticação
vi ~/work/fndni-decision-maker-master/DecisionMakerWebApp/WebContent/WEB-INF/classes/com/danter/google/auth/oauth.properties

# ===============================================
# Aceitar conexões no porto 443 no Tomcat
# ===============================================
1- Abrir o porto 443 na firewall

2- Forçar o re-encaminhamento do trafego 443 para 8443
	sudo iptables -t nat -A PREROUTING -p tcp --dport 443 -j REDIRECT --to-port 8443

Ver regras da firewall
	sudo iptables -L
	sudo iptables -t nat -L

Apagar as regras de re-encaminhamento da firewall
	sudo iptables -t nat -F

# ===============================================
# Excepções
# ===============================================

Internal Exception: java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost:3306/decision_maker

1- Garantir que o driver do MySQL está no directório "WEB-INF/lib" da Web App.
2- Garantir que as credenciais de acesso à BD são válidas. Basta as credenciais estarem errada para dar esta mensagem que induz em erro. 


