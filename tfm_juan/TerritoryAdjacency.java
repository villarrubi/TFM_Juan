package com.mycompany.tfm_juan;

import java.util.*;

/**
 * Define las adyacencias (fronteras) entre territorios del mapa
 */
public class TerritoryAdjacency {
    
    // Mapa de adyacencias: cada territorio tiene una lista de territorios vecinos
    private static final Map<String, List<String>> ADJACENCIES = new HashMap<>();
    
    static {
        // Aquí defines qué territorios son vecinos de cada uno
        // Ejemplo basado en tu mapa del Imperio Romano:
        
        // LUSITANIA es vecina de:
        addAdjacency("Lusitania", "Tarraconensis", "Baetica");
        
        // TARRACONENSIS es vecina de:
        addAdjacency("Tarraconensis", "Lusitania", "Baetica", "Narbonense", "Galia");
        
        // BAETICA es vecina de:
        addAdjacency("Baetica", "Lusitania", "Tarraconensis", "Mauritania");
        
        // MAURITANIA es vecina de:
        addAdjacency("Mauritania", "Baetica", "África");
        
        // ÁFRICA es vecina de:
        addAdjacency("África", "Mauritania", "Cirenaica", "Magna grecia");
        
        // CIRENAICA es vecina de:
        addAdjacency("Cirenaica", "África", "Egipto", "Magna grecia");
        
        // EGIPTO es vecino de:
        addAdjacency("Egipto", "Cirenaica", "Judea", "Asia");
        
        // JUDEA es vecina de:
        addAdjacency("Judea", "Egipto", "Siria");
        
        // SIRIA es vecina de:
        addAdjacency("Siria", "Judea", "Asia", "Armenia");
        
        // ASIA es vecina de:
        addAdjacency("Asia", "Egipto", "Siria", "Armenia", "Ponto", "Tracia", "Grecia");
        
        // PONTO es vecino de:
        addAdjacency("Ponto", "Asia", "Tracia");
        
        // ARMENIA es vecina de:
        addAdjacency("Armenia", "Asia", "Siria");
        
        // GRECIA es vecina de:
        addAdjacency("Grecia", "Macedonia", "Asia");
        
        // MACEDONIA es vecina de:
        addAdjacency("Macedonia", "Grecia", "Tracia", "Moesia", "Dalmacia");
        
        // TRACIA es vecina de:
        addAdjacency("Tracia", "Ponto", "Macedonia", "Moesia", "Asia");
        
        // DACIA es vecina de:
        addAdjacency("Dacia", "Moesia", "Panonia");
        
        // MOESIA es vecina de:
        addAdjacency("Moesia", "Tracia", "Macedonia", "Dacia", "Panonia", "Dalmacia");
        
        // DALMACIA es vecina de:
        addAdjacency("Dalmacia", "Macedonia", "Moesia", "Panonia", "Roma");
        
        // PANONIA es vecina de:
        addAdjacency("Panonia", "Dacia", "Moesia", "Dalmacia", "Raetia", "Galia Cisalpina");
        
        // MAGNA GRECIA es vecina de:
        addAdjacency("Magna grecia", "Africa", "Cirenaica", "Roma");
        
        // ROMA es vecina de:
        addAdjacency("Roma", "Magna grecia", "Galia Cisalpina");
        
        // GALIA CISALPINA es vecina de:
        addAdjacency("Galia Cisalpina", "Roma", "Panonia", "Raetia", "Narbonense", "Galia");
        
        // RAETIA es vecina de:
        addAdjacency("Raetia", "Panonia", "Galia Cisalpina", "Germania", "Galia");
        
        // GERMANIA es vecina de:
        addAdjacency("Germania", "Raetia", "Belgica");
        
        // NARBONENSE es vecina de:
        addAdjacency("Narbonense", "Tarraconensis", "Galia Cisalpina", "Galia");
        
        // GALIA es vecina de:
        addAdjacency("Galia", "Tarraconensis", "Narbonense", "Galia Cisalpina", "Raetia", "Belgica", "Britania");
        
        // BRITANIA es vecina de:
        addAdjacency("Britania", "Galia", "Belgica");
        
        //BELGICA es vecina de:
        addAdjacency("Belgica", "Britania", "Galia", "Germania");
    }
    
    /**
     * Método auxiliar para añadir adyacencias bidireccionales
     */
    private static void addAdjacency(String territory, String... neighbors) {
        ADJACENCIES.put(territory, Arrays.asList(neighbors));
    }
    
    /**
     * Obtiene la lista de territorios vecinos (adyacentes) a un territorio dado
     * @param territoryName Nombre del territorio
     * @return Lista de nombres de territorios vecinos
     */
    public static List<String> getAdjacentTerritories(String territoryName) {
        return ADJACENCIES.getOrDefault(territoryName, new ArrayList<>());
    }
    
    /**
     * Verifica si dos territorios son adyacentes (comparten frontera)
     * @param territory1 Nombre del primer territorio
     * @param territory2 Nombre del segundo territorio
     * @return true si son vecinos, false si no
     */
    public static boolean areAdjacent(String territory1, String territory2) {
        List<String> neighbors = ADJACENCIES.get(territory1);
        return neighbors != null && neighbors.contains(territory2);
    }
    
    /**
     * Obtiene los territorios enemigos adyacentes que se pueden atacar desde un territorio
     * @param from Territorio desde el que se ataca
     * @param allTerritories Mapa con todos los territorios del juego
     * @return Lista de territorios enemigos adyacentes
     */
    public static List<Territory> getAttackableNeighbors(Territory from, Map<String, Territory> allTerritories) {
        List<Territory> attackable = new ArrayList<>();
        List<String> adjacentNames = getAdjacentTerritories(from.getName());
        
        for (String adjName : adjacentNames) {
            Territory adjTerritory = allTerritories.get(adjName);
            if (adjTerritory != null && adjTerritory.getOwner() != from.getOwner()) {
                attackable.add(adjTerritory);
            }
        }
        
        return attackable;
    }
}