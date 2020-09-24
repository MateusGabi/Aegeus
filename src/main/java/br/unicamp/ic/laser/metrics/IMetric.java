/**
 * 
 */
package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.ServiceDescriptor;

/**
 * @author Mateus Gabi Moreira
 * @version 1.0.0
 */
public interface IMetric {
	public Double evaluate(ServiceDescriptor serviceDescriptor);
}
