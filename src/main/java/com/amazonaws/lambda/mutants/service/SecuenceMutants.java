package com.amazonaws.lambda.mutants.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.lambda.mutants.dto.AverageMutantsDTO;
import com.amazonaws.lambda.mutants.dto.MutantDTO;
import com.amazonaws.lambda.mutants.model.Coordinate;
import com.amazonaws.lambda.mutants.model.Direction;
import com.amazonaws.services.lambda.runtime.LambdaLogger;



public class SecuenceMutants {

	private   int secFound = 0;

	private admiCRUD adminCRUD=new admiCRUD();
	private MutantDTO mutantDTO;
	
	
	
	public AverageMutantsDTO getAverage() {
		
		AverageMutantsDTO adt=new AverageMutantsDTO();
		adt=adminCRUD.getAverage();
		return adt;
	}
	
	public  boolean validateMutants(String[] dna,LambdaLogger logger) {
		String sec1 = "";
		String sec2 = "";
		String secuenciaCompleta="";
		secFound = 0;
		Map<String, List<Coordinate>> mapa = new HashMap<String, List<Coordinate>>();
		mutantDTO=new MutantDTO();
		for (int j = 0; j < dna.length; j++) {
			sec1 = dna[j];
			secuenciaCompleta +=sec1+" ";
			secFound = ValidacionHorizontal(sec1);

			if (secFound == 2) {
				break;
			}

			if ((j + 1) < dna.length) {
				sec2 = dna[j + 1];
				
				for (int i = 0; i < dna[0].length(); i++) {
					if (i == 0) // Primer dato
					{
						
						if (Character.toString(sec2.charAt(i)).equalsIgnoreCase(Character.toString(sec1.charAt(i)))) {
							mapa = ValidacionArriba(mapa, Direction.A.toString(), j, i);
							if (secFound == 2) {
								break;
							}

						}

						if (Character.toString(sec2.charAt(i))
								.equalsIgnoreCase(Character.toString(sec1.charAt(i + 1)))) {

							mapa = ValidacionDerecha (mapa, Direction.AD.toString(), j, i);
							if (secFound == 2) {
								break;
							}
						}

					} else if (i == (dna[0].length() - 1)) // Ultimo dato de la cadena
					{
						
						if (Character.toString(sec2.charAt(i)).equalsIgnoreCase(Character.toString(sec1.charAt(i)))) {
							mapa = ValidacionArriba(mapa, Direction.A.toString(), j, i);

							if (secFound == 2) {
								break;
							}
						}

						if (Character.toString(sec2.charAt(i))
								.equalsIgnoreCase(Character.toString(sec1.charAt(i - 1)))) {

							mapa = ValidacionIzquierda(mapa, Direction.AI.toString(), j, i);

							if (secFound == 2) {
								break;
							}

						}

					} else {

						if (Character.toString(sec2.charAt(i)).equalsIgnoreCase(Character.toString(sec1.charAt(i)))) {

							mapa = ValidacionArriba(mapa, Direction.A.toString(), j, i);

							if (secFound == 2) {
								break;
							}
						}

						if (Character.toString(sec2.charAt(i))
								.equalsIgnoreCase(Character.toString(sec1.charAt(i - 1)))) {

							mapa = ValidacionIzquierda(mapa, Direction.AI.toString(), j, i);

							if (secFound == 2) {
								break;
							}
						}

						if (Character.toString(sec2.charAt(i))
								.equalsIgnoreCase(Character.toString(sec1.charAt(i + 1)))) {
							mapa = ValidacionDerecha(mapa, Direction.AD.toString(), j, i);
							if (secFound == 2) {
								break;
							}
						}

					}

				}

			}

			if (secFound == 2) {
				break;
			}
	
		}
		
	
			mutantDTO.setCadena(secuenciaCompleta);
		
		if (secFound == 2) {
			logger.log("Mutante encontrado");
			mutantDTO.setResultado(1);
			adminCRUD.guardarCadena(mutantDTO,logger);
			return true;
		} else {
			logger.log("Mutante no encontrado");
			mutantDTO.setResultado(1);
			adminCRUD.guardarCadena(mutantDTO,logger);
			return false;
		}

	}

	public  int ValidacionHorizontal(String cadena) {
		String TTTT = "TTTT";
		String GGGG = "GGGG";
		String AAAA = "AAAA";
		String CCCC = "CCCC";

		if (cadena.contains(TTTT)) {
			secFound++;
		}

		if (cadena.contains(GGGG)) {
			secFound++;
		}

		if (cadena.contains(AAAA)) {
			secFound++;
		}
		if (cadena.contains(CCCC)) {
			secFound++;
		}

		return secFound;
	}

	public  Map<String, List<Coordinate>> ValidacionArriba(Map<String, List<Coordinate>> mapa, String direction,
			int fil, int col) {

		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
		//logger.log("Entra a validar posicion arriba");
		
		if (mapa.containsKey(direction + " " + fil + " " + (col))) {
			coordinateList = mapa.remove(direction + " " + fil + " " + (col));
			if (coordinateList.size() >= 2) {
				//logger.log("agrega en map");
				coordinateList.add(new Coordinate(fil, col));
				coordinateList.add(new Coordinate((fil + 1), col));
				mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
				secFound++;
				//logger.log("contador validacion arriba: "+secFound);
			} else {
				coordinateList.add(new Coordinate(fil, (col)));
				mapa.put(direction + " " + (fil + 1) + " " + col, coordinateList);
			}
		} else {
			coordinateList = new ArrayList<Coordinate>();
			coordinateList.add(new Coordinate(fil, (col)));
			mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
		}
		return mapa;
	}

	public  Map<String, List<Coordinate>> ValidacionDerecha(Map<String, List<Coordinate>> mapa, String direction,
			int fil, int col) {

		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
		if (mapa.containsKey(direction + " " + fil + " " + (col + 1))) {
			coordinateList = mapa.remove(direction + " " + fil + " " + (col + 1));
			if (coordinateList.size() >= 2) {
				coordinateList.add(new Coordinate(fil, (col + 1)));
				coordinateList.add(new Coordinate((fil + 1), col));
				mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
				secFound++;
			} else {
				coordinateList.add(new Coordinate(fil, (col + 1)));
				mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
			}
		} else {
			coordinateList = new ArrayList<Coordinate>();
			coordinateList.add(new Coordinate(fil, (col + 1)));
			mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
		}

		return mapa;

	}

	public  Map<String, List<Coordinate>> ValidacionIzquierda(Map<String, List<Coordinate>> mapa,
			String direction, int fil, int col) {

		List<Coordinate> coordinateList = new ArrayList<Coordinate>();

		if (mapa.containsKey(direction + " " + fil + " " + (col - 1))) {
			coordinateList = mapa.remove(direction + " " + fil + " " + (col - 1));
			if (coordinateList.size() >= 2) {
				coordinateList.add(new Coordinate(fil, (col - 1)));
				coordinateList.add(new Coordinate((fil + 1), col));
				mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
				secFound++;
			} else {
				coordinateList.add(new Coordinate(fil, (col - 1)));
				mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
			}
		} else {
			coordinateList = new ArrayList<Coordinate>();
			coordinateList.add(new Coordinate(fil, (col - 1)));
			mapa.put(direction + " " + (fil + 1) + " " + (col), coordinateList);
		}
		return mapa;
	}

}
