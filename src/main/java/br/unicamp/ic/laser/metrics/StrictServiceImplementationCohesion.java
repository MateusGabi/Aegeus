/**
 * 
 */
package br.unicamp.ic.laser.metrics;

import java.util.ArrayList;import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Element;

import br.unicamp.ic.laser.model.Operation;
import br.unicamp.ic.laser.model.ServiceDescriptor;
import br.unicamp.ic.laser.utils.Reducible;
import br.unicamp.ic.laser.utils.ReducibleApplyFunction;

/**
 * Measurement Procedure: The measurement procedure consists of two steps.
 * Firstly, the chain of calls (which is defined as collaboration sequence
 * (cs) in section 4.1) is traced in order to determine the set of
 * implementation elements invoked in response to the invocation of each of
 * the service operations. Next, all the collaboration sequence sets are
 * intersected in a pair-wise manner and the intersected elements (represented
 * by black ovals in Figure 5) are placed in the overall intersected set
 * (duplicates are allowed). The cardinality of this set is then divided by the
 * total number of service elements multiplied by total number of operations.
 *
 * SSIC (s) = |IC(s)| / (|(BPSs U CsU IsU PsU Hs)| * | SO(sis )|)
 * 
 * @author Mateus Gabi Moreira
 */
public class StrictServiceImplementationCohesion implements IMetric {
	
	public Double evaluate(ServiceDescriptor serviceDescriptor) {

		List<Operation> operations = serviceDescriptor.getServiceOperations();
		
		if (operations != null && operations.size() == 0) {
			return new Double(0.0);
		}
		
		List<String> uniqueUsingTypes = operations
				.stream()
				.map(o -> o.getUsingTypesList())
				.flatMap(x -> x.stream())
				.distinct()
				.collect(Collectors.toList());
		
		int ammount = uniqueUsingTypes.size();
	    int numberOfOperations = operations.size();

	    // return pairs of operation names
	    val pairsOfKeys = pairs(structuredOperations.keys.toList())
//
//	    // list of architectural elements
//	    val intersectMutableList = mutableListOf<String>()
//	    pairsOfKeys.forEach { pair ->
//
//	        val op1 = structuredOperations[pair[0]]
//	        val op2 = structuredOperations[pair[1]]
//
//	        val intersectTypesIntoOperation = op1!!.intersect(op2!!)
//	        intersectTypesIntoOperation.forEach { intersectMutableList.add(it) }
//	    }
//
//	    // multiplica por dois porque é o conjunto de pares, então tanto a ida como a volta
//	    // ex: (Service::A-Service::B e Service::B-Service::A) contam.
//	    return (intersectMutableList.size * 2.0 / (sizeOfUnionSet * numberOfOperations))
		
		return null;
		
	}

}
