#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <iostream>
#include <string>
#include <semaphore.h>
#include <vector>
//atoi
#include <stdio.h>

#define restaurar "\033[1;0m"
#define added   "\033[32m"
#define debug "\033[1;31m"
#define eliminated    "\033[34m"

#define text1 "\033[35m"
#define text2    "\033[36m"
#define text3  "\033[33m"


using namespace std ;

int buffer_size;

int num_hebras_prod;
bool terminan_prod;
int ** id_hebras_prod;

int num_hebras_cons;
bool terminan_cons;
int ** id_hebras_cons;


int * buffer;


sem_t mutex ; // semaforo en memoria compartida



void* productor( void* p );

void imprimirBuffer_cambios(int cambio, bool producido);



void* productor( void* p ){

     bool terminar = false;

	int *n_Hebra;
	n_Hebra = (int *) p;
     //cout <<debug<< "Hebra_dentro: " << *n_Hebra <<restaurar<< endl;


	for(int i = 0; i <= buffer_size && !terminar; i++, i = i%buffer_size){
          if(terminan_prod){if(buffer_size-1 == i){terminar = true;}}

          sem_wait( &mutex );
          //if(buffer[i] != 1){

               //1) Cambiar el valor en la posición que toque a 1.
               buffer[i] = 1;

               //2) Imprimir el siguiente mensaje: HebraProductora(id_hebra) produce en posición "i"
               cout <<text3<< "HebraProductora(" << *n_Hebra << ") produce en posición \"" << i << "\"" <<restaurar<< endl;

               //3) Imprimir el estado actual del buffer, es decir, todos los valores en cada posición,
               //   y cambiar de color la posición del buffer que se acaba de producir.
               imprimirBuffer_cambios(i, true);

               //sleep(1);
          //}
          sem_post( &mutex );


	}


	return NULL ;
}

void* consumidor( void* p ){

     bool terminar = false;

	int *n_Hebra;
	n_Hebra = (int *) p;


	for(int i = 0; i <= buffer_size && !terminar; i++, i = i%buffer_size){
          if(terminan_cons){if(buffer_size-1 == i){terminar = true;}}

          if(buffer[i] != 0){

               //1) Cambiar el valor en la posición que toque a 0.
               buffer[i] = 0;

               //2) Imprimir el siguiente mensaje: HebraConsumidora(id_hebra) consume en posición "i"
               cout <<text1<< "HebraConsumidora(" << *n_Hebra << ") consume en posición \"" << i << "\"" <<restaurar<< endl;

               //3) Imprimir el estado actual del buffer, es decir, todos los valores en cada posición
               //   y cambiar de color la posición del buffer que se acaba de consumir.
               imprimirBuffer_cambios(i, false);

               sleep(1);
          }

	}


	return NULL ;
}



void imprimirBuffer_cambios(int cambio, bool producido){

     if(producido){
          cout << "Actual Buffer: ";
          for(int i = 0; i < buffer_size; i++){

               if(i == cambio){
                    cout << added << buffer[i] << " " << restaurar ;
               }else{
                    cout << buffer[i] << " ";
               }
          }
     }else{
          cout << "Actual Buffer: ";
          for(int i = 0; i < buffer_size; i++){

               if(i == cambio){
                    cout << eliminated << buffer[i] << " " << restaurar ;
               }else{
                    cout << buffer[i] << " ";
               }
          }
     }
     cout << endl << endl;

}

int main(int argc, char* argv[]){


     buffer_size = atoi(argv[1]);
     num_hebras_prod = atoi(argv[2]);
     terminan_prod = atoi(argv[3]);
     num_hebras_cons = atoi(argv[4]);
     terminan_cons = atoi(argv[5]);

     cout <<debug<< "buffer_size: " << buffer_size <<restaurar<< endl;
     cout <<debug<< "num_hebras_prod: " << num_hebras_prod <<restaurar<< endl;
     cout <<debug<< "terminan_prod: " << terminan_prod <<restaurar<< endl;
     cout <<debug<< "num_hebras_cons: " << num_hebras_cons <<restaurar<< endl;
     cout <<debug<< "terminan_cons: " << terminan_cons <<restaurar<< endl;

     buffer = new int[buffer_size];

     pthread_t id_hebra_prod[num_hebras_prod] ;
	pthread_t id_hebra_cons[num_hebras_cons] ;

     sem_init( &mutex, 0, 1 );

     id_hebras_prod = new int *[num_hebras_prod];
     for( unsigned i = 0 ; i < num_hebras_prod ; i++ ){
          id_hebras_prod[i] = new int;
          *id_hebras_prod[i] = i;

          //productor((void *) id_hebras_prod[i]);
          //cout <<debug<< "Hebra_fuera: " << *id_hebras_prod[i] <<restaurar<< endl;
          pthread_create( &(id_hebra_prod[i]), NULL, productor, (void *) id_hebras_prod[i] );
	}

     id_hebras_prod = new int *[num_hebras_cons];
     for( unsigned i = 0 ; i < num_hebras_cons ; i++ ){
          num_hebras_cons[i] = new int;
          *num_hebras_cons[i] = i;

          consumidor((void *) num_hebras_cons[i]);
          //pthread_create( &(id_hebra_prod[i]), NULL, consumidor, (void *) num_hebras_cons[i] );
     }

     int iret;

     for( unsigned i = 0 ; i < num_hebras_prod ; i++ ){
          iret = pthread_join( id_hebra_prod[i], NULL );
	}

     /*for( unsigned i = 0 ; i < num_hebras_cons ; i++ ){
          iret = pthread_join( id_hebra_cons[i], NULL );
     }*/


     sem_destroy( &mutex );

     destruirVctores(num_hebras_prod, );

}
