/**
 * Copyright (C) 2006 - 2012
 *   Pawel Kedzior
 *   Tomasz Kmiecik
 *   Kamil Pietak
 *   Krzysztof Sikora
 *   Adam Wos
 *   Lukasz Faber
 *   Daniel Krzywicki
 *   and other students of AGH University of Science and Technology.
 *
 * This file is part of AgE.
 *
 * AgE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AgE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AgE.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created: 2012-01-30
 * $Id: DefaultIslandAgent.java 471 2012-10-30 11:17:00Z faber $
 */

package org.jage.algorithms.jssp.jp.emas.rechenberg;

import com.google.common.collect.ImmutableList;
import org.jage.address.agent.AgentAddress;
import org.jage.address.agent.AgentAddressSupplier;
import org.jage.agent.ISimpleAgentEnvironment;
import org.jage.emas.agent.DefaultIslandAgent;
import org.jage.emas.agent.IndividualAgent;
import org.jage.emas.agent.IslandAgent;
import org.jage.platform.component.exception.ComponentException;
import org.jage.solution.ISolution;
import org.jage.solution.ISolutionFactory;
import org.jage.solution.IVectorSolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.collect.Iterables.filter;
import static java.lang.String.format;

/**
 * Default implementation of {@link org.jage.emas.agent.IslandAgent}.
 *
 * @author AGH AgE Team
 */
public class RechenbergDefaultIslandAgent extends DefaultIslandAgent implements IslandAgent {
    private static final Logger LOG = LoggerFactory.getLogger(RechenbergDefaultIslandAgent.class);
    private static final long serialVersionUID = 1L;

    private int epochCount = 0;
    private int bestFitness = Integer.MAX_VALUE;
    private boolean success = false;

    private static final Logger log = LoggerFactory.getLogger(RechenbergDefaultIslandAgent.class);

    @Inject
    private ISolutionFactory<ISolution> solutionFactory;

    private double avgChildEnergy;

    private double avgChildFitness;

    private IndividualAgent bestChild;

    private ISolution bestSolutionEver;

    private double bestFitnessEver;

    private long agentCounter;

    public RechenbergDefaultIslandAgent(final AgentAddress address) {
        super(address);
    }

    @Inject
    public RechenbergDefaultIslandAgent(final AgentAddressSupplier supplier) {
        super(supplier);
    }

    public double getAvgChildEnergy() {
        return avgChildEnergy;
    }

    public double getAvgFitness() {
        return avgChildFitness;
    }

    public IndividualAgent getBestChild() {
        return bestChild;
    }

    public ISolution getBestSolutionEver() {
        return bestSolutionEver;
    }

    public double getBestFitnessEver() {
        return bestFitnessEver;
    }

    @Override
    public ISimpleAgentEnvironment getEnvironment() {
        return super.getAgentEnvironment();
    }

    public void updateStatistics() {
        updateChildrenStats();
        updateBestStats();

        if (log.isDebugEnabled()) {
            log.debug(getStatisticsLog());
        }
    }

    @Override
    public boolean finish() throws ComponentException {
        log.info(getResultLog());
        return super.finish();
    }

    @Override
    public List<IndividualAgent> getIndividualAgents() {
        return ImmutableList.copyOf(filter(getAgents(), IndividualAgent.class));
    }

    private void updateChildrenStats() {
        final List<IndividualAgent> agents = getIndividualAgents();
        double totalChildEnergy = 0.0;
        double totalChildFitness = 0.0;

        for (final IndividualAgent agent : agents) {
            totalChildEnergy += agent.getEnergy();
            totalChildFitness += agent.getOriginalFitness();
        }

        final int size = agents.size();
        avgChildEnergy = totalChildEnergy / size;
        avgChildFitness = totalChildFitness / size;
        agentCounter += size;
    }

    private static Map<RechenbergDefaultIslandAgent, Double> diversity = new HashMap<RechenbergDefaultIslandAgent, Double>();
    private static Map<RechenbergDefaultIslandAgent, Integer> popSize = new HashMap<RechenbergDefaultIslandAgent, Integer>();
    private static Timer timer;
    private static long startTime;

    private synchronized double getDiversity() {
        double div = 0.0;
        for (Double aDouble : diversity.values()) {
            div += aDouble;
        }
        div /= (double) diversity.size();
        return div;
    }

    private synchronized int getPopSize() {
        int popSize = 0;
        for (Integer aInteger : RechenbergDefaultIslandAgent.popSize.values()) {
            popSize += aInteger;
        }
        return popSize;
    }

    private synchronized void putDiv(RechenbergDefaultIslandAgent a, Double d) {
        diversity.put(a, d);
    }

    private synchronized void putPop(RechenbergDefaultIslandAgent a, Integer i) {
        popSize.put(a, i);
    }

    private void calculateStandardDev(final List<IndividualAgent> population) {
        try {
            int size = ((IVectorSolution<Double>) population.iterator().next().getSolution()).getRepresentation().size();

            double[] sums = new double[size];
            double[] avgs = new double[size];
            double[] standardDevs = new double[size];

            for (final IndividualAgent solution : population) {
                IVectorSolution<Double> s = (IVectorSolution<Double>) solution.getSolution();
                List<Double> representation = s.getRepresentation();
                for (int i = 0; i < representation.size(); i++) {
                    sums[i] += representation.get(i);
                }
            }
            for (int i = 0; i < sums.length; i++) {
                avgs[i] = sums[i] / population.size();
            }

            for (final IndividualAgent solution : population) {
                IVectorSolution<Double> s = (IVectorSolution<Double>) solution.getSolution();
                List<Double> representation = s.getRepresentation();
                for (int i = 0; i < representation.size(); i++) {
                    standardDevs[i] += Math.pow(representation.get(i) - avgs[i], 2);
                }
            }
            double currentDiversity = 0;
            for (int i = 0; i < standardDevs.length; i++) {
                standardDevs[i] /= population.size();
                standardDevs[i] = Math.sqrt(standardDevs[i]);
                currentDiversity += standardDevs[i];
            }
            currentDiversity /= (double) population.size();
            putDiv(this, currentDiversity);
            putPop(this, population.size());

            if (timer == null) {
                startTime = System.currentTimeMillis();

                timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        updateStatistics();
                        LOG.warn("{};{}", (System.currentTimeMillis() - startTime) / 1000., getDiversity());
                        //LOG.warn("{};{}", (System.currentTimeMillis() - startTime) / 1000., getPopSize());
                    }
                }, 0, 500);
            }
        } catch (RuntimeException e) {
            LOG.error("REx {}", e);
        }
    }

    private void updateBestStats() {
        calculateStandardDev(getIndividualAgents());

        for (final IndividualAgent agent : getIndividualAgents()) {
            int originalFitness = (int) agent.getOriginalFitness();

            if (originalFitness < bestFitness) {
                success = true;
                bestFitness = originalFitness;
            }

            if (bestChild == null || originalFitness > bestChild.getOriginalFitness()) {
                bestChild = agent;
            }
        }

        if (bestChild != null && (bestSolutionEver == null || bestChild.getOriginalFitness() > bestFitnessEver)) {
            bestSolutionEver = solutionFactory.copySolution(bestChild.getSolution());
            bestFitnessEver = bestChild.getOriginalFitness();
        }

        epochCount++;
        if (epochCount >= 30) {
            if (success) {
                Settings.increase();
            } else {
                Settings.decrease();
            }
            success = false;
            epochCount = 0;
        }
    }

    public String getStatisticsLog() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getAddress() + " at step " + (getStep() - 1) + "\n")
                .append("\tCurrent agent count: " + getAgents().size() + "\n")
                .append("\tAverage agent count ever: " + (double) agentCounter / getStep() + "\n")
                .append("\tAverage agent energy: " + getAvgChildEnergy() + "\n")
                .append("\tAverage agent fitness: " + getAvgFitness() + "\n")
                .append("\tCurrent best fitness: " + getBestChild().getOriginalFitness() + "\n")
                .append("\tBest fitness ever: " + getBestFitnessEver() + "\n");
        return builder.toString();
    }

    private String getResultLog() {
        final StringBuilder builder = new StringBuilder("\n\t---=== Computation finished ===---");
        builder.append(format("\n\tBest solution ever (evaluation = %1$.2f): %2$s", bestFitnessEver, bestSolutionEver));
        return builder.toString();
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("address", getAddress()).toString();
    }
}