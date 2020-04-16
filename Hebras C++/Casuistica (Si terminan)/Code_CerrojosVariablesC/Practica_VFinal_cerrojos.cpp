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
int num_hebras_prod_restantes;
bool terminan_prod;
int ** id_hebras_prod;

int num_hebras_cons;
int num_hebras_cons_restantes;
bool terminan_cons;
int ** id_hebras_cons;

int * buffer;

int huecos_disponibles;


int entrada_P;
int entrada_C;




void* productor( void* p );
void* consumidor( void* p );

void imprimirBuffer_cambios(int cambio, bool producido);

void funcion_wait (int i);
void funcion_post (int i);
void funcion_init ();
void funcion_destroy ();
void deleteId_hebras_prod();
void deleteId_hebras_cons();


pthread_mutex_t mutex= PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t lleno= PTHREAD_COND_INITIALIZER;
pthread_cond_t vacio= PTHREAD_COND_INITIALIZER;

int contador;


void* productor( void* p ){

     bool terminar = false;

	int *n_Hebra;
	n_Hebra = (int *) p;
     int id_hebra = *n_Hebra;

     if(!terminan_prod){
          while(1){

               pthread_mutex_lock(&mutex);
               while (contador==buffer_size) {
                    pthread_cond_wait(&lleno, &mutex);
               }

               buffer[entrada_P] = 1;

               cout <<text3<< "HebraProductora(" << id_hebra << ") prduce en posición \"" << entrada_P << "\"" <<restaurar<< endl;
               imprimirBuffer_cambios(entrada_P, true);

               entrada_P++;
               entrada_P = entrada_P%buffer_size;
               contador ++;

               //sleep(1);

               pthread_cond_broadcast(&vacio);
               pthread_mutex_unlock(&mutex);

          }
     }else{

          pthread_mutex_lock(&mutex);
          while (contador==buffer_size && num_hebras_cons_restantes != 0) {
               pthread_cond_wait(&lleno, &mutex);
          }

          if(num_hebras_cons_restantes != 0 || huecos_disponibles != buffer_size ){

               buffer[entrada_P] = 1;

               cout <<text3<< "HebraProductora(" << id_hebra << ") prduce en posición \"" << entrada_P << "\"" <<restaurar<< endl;
               imprimirBuffer_cambios(entrada_P, true);

               entrada_P++;
               entrada_P = entrada_P%buffer_size;
               contador ++;

               num_hebras_prod_restantes--;
               huecos_disponibles++;

               //sleep(1);

          }

          pthread_cond_broadcast(&vacio);
          pthread_mutex_unlock(&mutex);

     }

	return NULL ;
}

void* consumidor( void* p ){

     bool terminar = false;

     int *n_Hebra;
	n_Hebra = (int *) p;
     int id_hebra = *n_Hebra;

     if(!terminan_cons){
          while(1){

               pthread_mutex_lock(&mutex);
               while (contador==0) {
                    pthread_cond_wait(&vacio, &mutex);
               }

               buffer[entrada_C] = 0;

               cout <<text2<< "HebraConsumidora(" << id_hebra << ") consume en posición \"" << entrada_C << "\"" <<restaurar<< endl;
               imprimirBuffer_cambios(entrada_C, false);

               entrada_C++;
               entrada_C = entrada_C%buffer_size;
               contador --;

               //sleep(1);

               pthread_cond_broadcast(&lleno);
               pthread_mutex_unlock(&mutex);

          }
     }else{

          pthread_mutex_lock(&mutex);
          while (contador==0 && num_hebras_prod_restantes != 0) {
               pthread_cond_wait(&vacio, &mutex);
          }

          if(num_hebras_prod_restantes != 0 || huecos_disponibles != 0 ){


               buffer[entrada_C] = 0;

               cout <<text2<< "HebraConsumidora(" << id_hebra << ") consume en posición \"" << entrada_C << "\"" <<restaurar<< endl;
               imprimirBuffer_cambios(entrada_C, false);

               entrada_C++;
               entrada_C = entrada_C%buffer_size;
               contador --;

               num_hebras_cons_restantes--;
               huecos_disponibles--;


               //sleep(1);

          }

          pthread_cond_broadcast(&lleno);
          pthread_mutex_unlock(&mutex);
     }



	return NULL ;
}


void deleteId_hebras_prod(){

     for( int i = 0 ; i < num_hebras_prod ; i++ ){
          delete id_hebras_prod[i];
	}

     delete id_hebras_prod;

}

void deleteId_hebras_cons(){

     for( int i = 0 ; i < num_hebras_cons ; i++ ){
          delete id_hebras_cons[i];
     }
     delete id_hebras_cons;

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

     num_hebras_prod_restantes = num_hebras_prod;
     num_hebras_cons_restantes = num_hebras_cons;

     pthread_t id_hebra_prod[num_hebras_prod] ;
	pthread_t id_hebra_cons[num_hebras_cons] ;

     id_hebras_prod = new int *[num_hebras_prod];
     for( unsigned i = 0 ; i < num_hebras_prod ; i++ ){
          id_hebras_prod[i] = new int;
          *id_hebras_prod[i] = i;

          pthread_create( &(id_hebra_prod[i]), NULL, productor, (void *) id_hebras_prod[i] );

          //productor(id_hebras_prod[i]);

	}


     id_hebras_cons = new int *[num_hebras_cons];
     for( unsigned i = 0 ; i < num_hebras_cons ; i++ ){
          id_hebras_cons[i] = new int;
          *id_hebras_cons[i] = i;

          pthread_create( &(id_hebra_cons[i]), NULL, consumidor, (void *) id_hebras_cons[i] );

          //consumidor(id_hebras_cons[i]);

     }

     for( int i = 0 ; i < num_hebras_prod ; i++ ){
          pthread_join( id_hebra_prod[i], NULL );
	}

     for( int i = 0 ; i < num_hebras_cons ; i++ ){
          pthread_join( id_hebra_cons[i], NULL );
     }

     //funcion_destroy();




     delete buffer;


     deleteId_hebras_prod();
     deleteId_hebras_cons();

}
