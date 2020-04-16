# Makefilecon diferentes funciones para trabajar los diferentes codigos creados


#Declaramos distintas variables
Objc = TodosLos.o
Name = Semaforos
Name_2 = Cerrojos_VariablesC
Name_3 = Flipadas_Semaforos

#-------------------------------------------------------------------

#Llamamos como predeterminado a la función principal de compilación y de información
all: info Compilar

#-------------------------------------------------------------------
	
#Compilación de los diferentes .o en un solo ejecutable
Compilar:
	@g++ Code_CerrojosVariablesC/*.cpp -pthread -o $(Name_1)
	@mv $(Name_1) TemporalFiles
	@g++ Code_Semaforos/*.cpp -pthread -o $(Name_2)
	@mv $(Name_2) TemporalFiles
	@g++ Code_Flipadas/*.cpp -pthread -o $(Name_3)
	@mv $(Name_3) TemporalFiles


#-------------------------------------------------------------------

#Sacamos por pantalla la información de las diferentes funciones implementadas en este Makefile
info:
	clear
	@echo "Funciones disponibles: "
	@echo 
	@echo "- make info : Saca por pantalla las diferentes funciones del archivo Makefile"
	@echo "- make clean : Limpia la carpeta de Archivos Temporales"
	@echo "- make exe : Ejecuta el programa"
	@echo "- make exe2 : Ejecuta el programa de debuggeo"
	@echo


#-------------------------------------------------------------------

#Removemos (eliminamos) los archivos temporales (los ".o")
clean: 
	rm -f TemporalFiles/*.o
	rm -f TemporalFiles/$(Name)

#-------------------------------------------------------------------

#Ejecutamos el Programa
exe:
	@./TemporalFiles/$(Name)


exe2:
	@./TemporalFiles/$(Name_2)


exe3:
	@./TemporalFiles/$(Name_3)

