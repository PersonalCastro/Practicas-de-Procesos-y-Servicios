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
sem_t * mutex ;
sem_t output;




void* productor( void* p );
void* consumidor( void* p );

void imprimirBuffer_cambios(int cambio, bool producido);

void funcion_wait (int i);
void funcion_post (int i);
void funcion_init ();
void funcion_destroy ();


void* productor( void* p ){

     bool terminar = false;

	int *n_Hebra;
	n_Hebra = (int *) p;
     int id_hebra = *n_Hebra;



	for(int i = 0; i <= buffer_size && !terminar; i++, i = i%buffer_size){
          if(terminan_prod){if(buffer_size-1 == i){terminar = true;}}

          funcion_wait(i);
          //cout <<text3<< "HebraProductora(" << id_hebra << ")"<<restaurar<< endl;

          if(buffer[i] != 1){

               //1) Cambiar el valor en la posición que toque a 1.
               buffer[i] = 1;


               //2) Imprimir el siguiente mensaje: HebraProductora(id_hebra) produce en posición "i"
               cout <<text3<< "HebraProductora(" << id_hebra << ") produce en posición \"" << i << "\"" <<restaurar<< endl;

               //3) Imprimir el estado actual del buffer, es decir, todos los valores en cada posición,
               //   y cambiar de color la posición del buffer que se acaba de producir.
               imprimirBuffer_cambios(i, true);


               //sleep(1);
          }
          funcion_post(i);


	}


	return NULL ;
}

void* consumidor( void* p ){

     bool terminar = false;

     int *n_Hebra;
	n_Hebra = (int *) p;
     int id_hebra = *n_Hebra;



	for(int i = 0; i <= buffer_size && !terminar; i++, i = i%buffer_size){
          if(terminan_cons){if(buffer_size-1 == i){terminar = true;}}

          funcion_wait(i);
          //cout <<text1<< "HebraConsumidora(" << id_hebra << ")"<<restaurar<< endl;
          if(buffer[i] != 0){

               //1) Cambiar el valor en la posición que toque a 0.
               buffer[i] = 0;


               //2) Imprimir el siguiente mensaje: HebraConsumidora(id_hebra) consume en posición "i"
               cout <<text1<< "HebraConsumidora(" << id_hebra << ") consume en posición \"" << i << "\"" <<restaurar<< endl;

               //3) Imprimir el estado actual del buffer, es decir, todos los valores en cada posición
               //   y cambiar de color la posición del buffer que se acaba de consumir.
               imprimirBuffer_cambios(i, false);


               //sleep(1);
          }
          funcion_post(i);

	}


	return NULL ;
}



void funcion_wait (int i){

     sem_wait( &mutex[i]);
     sem_wait( &output );

}

void funcion_post (int i){

     sem_post( &mutex[i] );
     sem_post( &output );

}

void funcion_init (){

     for(int i = 0; i < buffer_size; i++){

          sem_init( &mutex[i], 0, 1 );
     }

}

void funcion_destroy (){

     for(int i = 0; i < buffer_size; i++){

          sem_destroy( &mutex[i]);
     }

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

     /*cout <<debug<< "buffer_size: " << buffer_size <<restaurar<< endl;
     cout <<debug<< "num_hebras_prod: " << num_hebras_prod <<restaurar<< endl;
     cout <<debug<< "terminan_prod: " << terminan_prod <<restaurar<< endl;
     cout <<debug<< "num_hebras_cons: " << num_hebras_cons <<restaurar<< endl;
     cout <<debug<< "terminan_cons: " << terminan_cons <<restaurar<< endl;*/

     buffer = new int[buffer_size];
     mutex = new sem_t[buffer_size];

     sem_init( &output, 0, 1 );
     funcion_init();

     pthread_t id_hebra_prod[num_hebras_prod] ;
	pthread_t id_hebra_cons[num_hebras_cons] ;

     id_hebras_prod = new int *[num_hebras_prod];
     for( unsigned i = 0 ; i < num_hebras_prod ; i++ ){
          id_hebras_prod[i] = new int;
          *id_hebras_prod[i] = i;
          //cout << "id_hebra_prod" << i << endl;
          //productor((void *) &i);
          pthread_create( &(id_hebra_prod[i]), NULL, productor, (void *) id_hebras_prod[i] );
	}


     id_hebras_cons = new int *[num_hebras_cons];
     for( unsigned i = 0 ; i < num_hebras_cons ; i++ ){
          id_hebras_cons[i] = new int;
          *id_hebras_cons[i] = i;
          //cout << "id_hebra_cons" << i << endl;
          //consumidor((void *) &i);
          pthread_create( &(id_hebra_cons[i]), NULL, consumidor, (void *) id_hebras_cons[i] );
     }

     //sleep(5);
     for( int i = 0 ; i < num_hebras_prod ; i++ ){
          pthread_join( id_hebra_prod[i], NULL );
	}

     for( int i = 0 ; i < num_hebras_cons ; i++ ){
          pthread_join( id_hebra_cons[i], NULL );
     }

     funcion_destroy();
     sem_destroy( &output );

}
