/**
 * 
 */
package br.unicamp.ic.laser.metrics;

import br.unicamp.ic.laser.model.IServiceDescriptor;

/**
 * @author Mateus Gabi Moreira
 * @version 1.0.0
 */
public interface IMetric {
	public Double evaluate(IServiceDescriptor serviceDescriptor);
}
