/**
 * 	@Name: Estanque
 *	@Author: PersonalCastro
 *	@Brief: (traducir al inglés) Estealgoritmo esta basado en el algoritmode ordenacion llamado "burbuja"
 *		su principal problema esel tiempo estimado de duración y hepensado que si se mejoraba de alguna forma
 *		podria dar buenos resultados.
 *		Este consta de su movimiento principal donde (A,B) "A" y "B" son cambiados de posición en cuanto uno de estos
 *		lo requiere (ya dependiendo de la referencia de ordenacionque se tenga en ese momento).
 *		La formadde mejorarlo estara en que cuando se efectue este intercambio se llamara a una funció recursiva alterna
 *		que comprobara si se puede efectuar ese mismo movimiento pero en sentido contrario.
 *
 *		"Lo que mas me gusto, fue poder ver todas esas ondas creadas a partir de una sola piedrecita".
 *
 *
 *	@Note: Se ordenara de menor a mayor [-33,-32,-31, ... , 0 , ... ,31,32,33]
 */

#include <iostream>
#include <stdlib.h>


#define restaurar "\033[1;0m"
#define debug "\033[1;31m"
#define rojo     "\033[31m"      /* Red */
#define verde   "\033[32m"      /* Green */

using namespace std;

/**
 * @brief Procedimiento que genera numeros aleatorios al vector
 * @param El vector ya mencionado
 * @param Su dimensión
 */
void randomVGenerator( int * vector, int dim );

/**
 * @brief Procedimiento que saca por pantalla el estado (valores internos) del vector
 * @param El vector ya mencionado
 * @param Su dimensión
 */
void printV( int * vector, int dim );

/**
 * @brief Procedimiento que comprueba el estado final del vector para saber si esta bien ordenado
 * @param El vector ya mencionado
 * @param Su dimensión
 */
void checking( int * vector, int dim );


void rightEstanque( int * vector, int dim, int movePosition, int finalEspecificoRight, int finalEspecificoLeft );
void leftEstanque( int * vector, int dim, int movePosition, int finalEspecificoRight, int finalEspecificoLeft );


int main (int argc, char* argv[]) {


	int dim = 5;

	int * vector;
	vector = new int [dim];

  vector[0] = atoi(argv[1]);
  vector[1] = atoi(argv[2]);
  vector[2] = atoi(argv[3]);
  vector[3] = atoi(argv[4]);
  vector[4] = atoi(argv[5]);


	rightEstanque(vector, dim, 0, (dim - 1), 0);
  printV(vector, dim);
  
  return 2;
}


void printV(int * vector, int dim) {

//	cout << debug << "Debug: printV()" << restaurar << endl;

    cout << endl;

    for (int i = 0; i < dim; i++){

        cout << vector[i] << "-";
    }

    cout << endl;

}


/* ------------------------------------- */
/* |             Estanque              | */
/* ------------------------------------- */

void rightEstanque(int * vector, int dim, int movePosition, int finalEspecificoRight, int finalEspecificoLeft) {

	if (movePosition != (dim - 1) && movePosition != finalEspecificoRight) {

		if (vector[movePosition] > vector[movePosition + 1]) {

			int aux = 0;

			aux = vector[movePosition];
			vector[movePosition] = vector[movePosition + 1];
			vector[movePosition + 1] = aux;

			leftEstanque(vector, dim, movePosition, movePosition, finalEspecificoLeft);
		}

		rightEstanque(vector, dim, movePosition + 1, finalEspecificoRight, finalEspecificoLeft);
	}
}

void leftEstanque(int * vector, int dim, int movePosition, int finalEspecificoRight, int finalEspecificoLeft) {

	if (movePosition != 0) {

		if (vector[movePosition] < vector[movePosition - 1]) {

			int aux = 0;

			aux = vector[movePosition];
			vector[movePosition] = vector[movePosition - 1];
			vector[movePosition - 1] = aux;

			rightEstanque(vector, dim, movePosition, finalEspecificoRight, movePosition);
		}

		leftEstanque(vector, dim, movePosition - 1, finalEspecificoRight, finalEspecificoLeft);
	}
}
