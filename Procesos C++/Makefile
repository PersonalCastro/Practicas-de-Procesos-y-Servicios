# Makefilecon diferentes funciones para trabajar los diferentes codigos creados


#Declaramos distintas variables
Objc = TodosLos.o
Name = Satelite
Name_2 = SateliteWithoutSpace

#-------------------------------------------------------------------

#Llamamos como predeterminado a la función principal de compilación y de información
all: info Compilar

#-------------------------------------------------------------------

#Compilación de los diferentes .o en un solo ejecutable
Compilar:
	@g++ Code/*.cpp -lcurses -o $(Name)
	@mv $(Name) TemporalFiles
	@g++ Code_2/*.cpp -lcurses -o $(Name_2)
	@mv $(Name_2) TemporalFiles

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
	@echo "PRECONDICIONES: descargar la libreria Curses en el ordenador"
	@echo "Descarga en Linux --> sudo apt-get install libncurses5-dev"
	@echo "Descarga en MacOs --> sudo brew install ncurses"
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
