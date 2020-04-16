#include <iostream>
#include <unistd.h>
#include <sys/wait.h>
#include <csignal>

using namespace std;

#define restaurar "\033[1;0m"
#define bienC   "\033[32m"
#define malC "\033[1;31m"
#define azul    "\033[34m"

#define debug     "\033[31m"
#define primeroC "\033[35m"
#define segundoC    "\033[36m"
#define terceroC  "\033[33m"

bool * signalBuenaRecivida;
bool lookAtSteps = true;

string initPrintRessultProces(string numeros_introducidos);
void failedToFork();

void manejaBien(int segnal);
void manejaMal(int segnal);

void printCadenaActual (string cadenaCompletada, string cadena_add);
bool comprobar_numeros_introducidos(string numeros_introducidos);
string calcularResultado(char * buffer);



int main(){

     string db_numeros = "";
     string numeros = "";
     int entero = 0;

     while(1){
		system("clear");
		system("clear");

		if(entero == -1){
			db_numeros += initPrintRessultProces(numeros);
			numeros = "";
		}
          printCadenaActual(db_numeros, numeros);
		cin >> entero;
		numeros += to_string(entero) + " ";

	}

}

void printCadenaActual (string cadenaCompletada, string cadena_add){

     bool g_detectada = false;
     bool b_detectada = false;

     for (int i = 0; i < cadenaCompletada.size(); i++) {

          if(cadenaCompletada[i] == 'g'){
               g_detectada= true;
               b_detectada = false;
          }else if (cadenaCompletada[i] == 'b'){
               g_detectada= false;
               b_detectada = true;
          }else{

               if(g_detectada){
                    cout << bienC << cadenaCompletada[i] << restaurar;
               }else if(b_detectada){
                    cout << malC << cadenaCompletada[i] << restaurar;
               }else{
                    cout << azul << cadenaCompletada[i] << restaurar;
               }
          }
     }
     cout << azul << cadena_add << restaurar;
}


/*   SEÑALES   */

void manejaBien(int segnal){
     if(lookAtSteps){
          cout << debug << "Se ha recivido SIGUSR1: " << segnal << " Buena respuesta."  << restaurar << endl;
     }
     signalBuenaRecivida = new bool;
     *signalBuenaRecivida = true;
}

void manejaMal(int segnal){
     if(lookAtSteps){
          cout << debug << "Se ha recivido SIGUSR2: " << segnal << " Mala respuesta."  << restaurar << endl;
     }
     signalBuenaRecivida = new bool;
     *signalBuenaRecivida = false;
}


/*   PROCESOS   */

void failedToFork(){
	cerr << "Failed to fork" << endl;
	exit(1);
}

string initPrintRessultProces(string numeros_introducidos){
     if(lookAtSteps){
          cout << debug << "Entramos en los nuevos procesos." << restaurar << endl;
     }

     string stringDevuelto = "";

     int pipeAllDataNumeros[2];
     pipe(pipeAllDataNumeros);
     char * buffer;
     buffer = new char[numeros_introducidos.size()];

     int pipeResult[2];
     pipe(pipeResult);
     char * buffer2;
     buffer2 = new char[numeros_introducidos.size()];

     pid_t primero, segundo, tercero;
     primero = getpid();

     segundo = fork();
     if (segundo == -1){
          failedToFork();
     }else if (segundo == 0){

          tercero = fork();
          if (tercero == -1){
               failedToFork();
          }else if (tercero == 0){
               if(lookAtSteps){
                    cout <<  endl << terceroC << "\t\tEntramos en Tercero." << restaurar << endl;
               }

               close(pipeAllDataNumeros[1]);
               read(pipeAllDataNumeros[0], buffer, numeros_introducidos.size());
               if(lookAtSteps){
                    cout << terceroC << "\t\tNieto recive: " << buffer << restaurar << endl;
               }
               string strResultado= calcularResultado(buffer);

               for (int i = 0; i < strResultado.size(); i++) {
                    buffer2[i] = strResultado[i];
               }

               if(lookAtSteps){
                    cout << terceroC << "\t\tNieto Calcula: " << buffer2 << restaurar << endl;
               }
               if(lookAtSteps){
                    cout << terceroC << "\t\tNieto empieza a pasar los calculos... " << restaurar << endl;
               }

               close(pipeResult[0]);
               write(pipeResult[1], buffer2, numeros_introducidos.size());
               if(lookAtSteps){
                    cout << terceroC << "\t\tNieto termina de pasar los calculos... " << restaurar << endl;
               }

               if(lookAtSteps){
                    cout <<  endl << terceroC << "\t\tSalimos de Tercero." << restaurar << endl;
               }
               exit(0);
          }else{

          }



          if(lookAtSteps){
               cout <<  endl << segundoC << "\tEntramos en Segundo." << restaurar << endl;
          }

          //Si da fallo el Programa y en la terminal aparece el siguiente mensaje: "Señal generada por el usuario 1"
          //Descomentar la siguiente linea:
          usleep(200);

          if(lookAtSteps){
               cout << segundoC << "\t¿El hijo tiene los numeros? " << numeros_introducidos << restaurar << endl;
          }

          bool numeros_introducidos_bien = comprobar_numeros_introducidos(numeros_introducidos);
          if (numeros_introducidos_bien){
               close(pipeAllDataNumeros[0]);
               for (int i = 0; i < numeros_introducidos.size(); i++) {
                    buffer[i] = numeros_introducidos[i];
               }
               if(lookAtSteps){
                    cout << segundoC << "\tHijo empieza a pasar los datos iniciales al Nieto... " << restaurar << endl;
               }
               write(pipeAllDataNumeros[1], buffer, numeros_introducidos.size());
               if(lookAtSteps){
                    cout << segundoC << "\tHijo termina de pasar los datos iniciales al Nieto... " << restaurar << endl;
               }
               if(lookAtSteps){
                    cout << segundoC << "\tHijo Manda una Señal al padre (Buena) " << restaurar << endl;
               }
               kill(primero, SIGUSR1);
          }else{
               if(lookAtSteps){
                    cout << segundoC << "\tHijo Manda una Señal al padre (Mala) " << restaurar << endl;
               }
               kill(primero, SIGUSR2);
               if(lookAtSteps){
                    cout << segundoC << "\tHijo destruye el proceso Nieto " << restaurar << endl;
               }
               kill(tercero, SIGTERM);
          }


          if(lookAtSteps){
               cout << segundoC << "\tSalimos de Segundo." << restaurar << endl;
          }
          exit(0);
     }else{
          if(lookAtSteps){
               cout << endl << primeroC << "Entramos en Primero." << restaurar << endl;
          }

          signal(SIGUSR1, manejaBien);
          signal(SIGUSR2, manejaMal);

          if(lookAtSteps){
               cout << primeroC << "Señales creadas." << restaurar << endl;
          }
          pause();
          if(lookAtSteps){
               cout << primeroC << "Pause Terminado." << restaurar << endl;
          }

          if(*signalBuenaRecivida){
               if(lookAtSteps){
                    cout << primeroC << "Señal buena." << restaurar << endl;
               }
               if(lookAtSteps){
                    cout << primeroC << "Pidiendo calculo.." << restaurar << endl;
               }

               close(pipeResult[1]);
               read(pipeResult[0], buffer2, numeros_introducidos.size());
               string CalculoRecivido = buffer2;

               if(lookAtSteps){
                    cout << primeroC << "CalculoRecivido : " << CalculoRecivido << "  Size: " << CalculoRecivido.size() << restaurar << endl;
               }
               stringDevuelto = "g" + numeros_introducidos + "(" + CalculoRecivido + ") ";
               if(lookAtSteps){
                    cout << primeroC << "Padre devuelve esta cadena: " << stringDevuelto << restaurar << endl;
               }
          }else{
               if(lookAtSteps){
                    cout << primeroC << "Señal mala." << restaurar << endl;
               }
               stringDevuelto = "b" + numeros_introducidos;
               if(lookAtSteps){
                    cout << primeroC << "Padre devuelve esta cadena: " << stringDevuelto << restaurar << endl;
               }
          }

          wait(0);
          if(lookAtSteps){
               cout << primeroC << "Salimos de Primero." << restaurar << endl;
          }

     }
     return stringDevuelto;
}



bool comprobar_numeros_introducidos(string numeros_introducidos){

     bool numeros_introducidos_bien = true;
     int cantidad_negativos = 0;


     for (int i = 0; i < numeros_introducidos.size(); i++) {

          if(numeros_introducidos[i] == '-'){
               cantidad_negativos++;
          }
     }

     if(cantidad_negativos > 1) {
          numeros_introducidos_bien = false;
     }


     return numeros_introducidos_bien;
}

string calcularResultado(char * buffer){

     int res = 0;
     string actualBuffer = buffer;

     string numero = "";

     for (int i = 0; (i < actualBuffer.size()) && (actualBuffer[i] != '-') ; i++) {

          if(actualBuffer[i] == ' '){
               res += stoi(numero);
               numero = "";
          }else{
               numero += actualBuffer[i];
          }


     }

     return to_string(res);
}
