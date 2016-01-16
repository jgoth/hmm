package io.github.adrianulbona.hmm.sample;

import io.github.adrianulbona.hmm.*;
import io.github.adrianulbona.hmm.probability.ProbabilityCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by adrianbona on 1/14/16.
 */
public enum WikipediaViterbi {
	INSTANCE;

	public final Model<MedicalState, Symptom> model;

	WikipediaViterbi() {
		final ProbabilityCalculator<MedicalState, Symptom> probabilityCalculator = new ProbabilityCalculator<>(
				StartProbabilities.INSTANCE.data::get,
				EmissionProbabilities.INSTANCE.data::get,
				TransitionProbabilities.INSTANCE.data::get);
		final ReachableStateFinder<MedicalState, Symptom> reachableStateFinder = observation -> asList(MedicalState.values());
		this.model = new Model<>(probabilityCalculator, reachableStateFinder);
	}

	public enum MedicalState implements State {
		HEALTHY,
		FEVER
	}

	public enum Symptom implements Observation {
		NORMAL,
		COLD,
		DIZZY
	}

	public enum StartProbabilities {
		INSTANCE;

		public final Map<MedicalState, Double> data;

		StartProbabilities() {
			data = new HashMap<>();
			data.put(MedicalState.HEALTHY, 0.6);
			data.put(MedicalState.FEVER, 0.4);
		}
	}

	public enum TransitionProbabilities {
		INSTANCE;

		public final Map<Transition<MedicalState>, Double> data;

		TransitionProbabilities() {
			data = new HashMap<>();
			data.put(new Transition<>(MedicalState.HEALTHY, MedicalState.HEALTHY), 0.7);
			data.put(new Transition<>(MedicalState.HEALTHY, MedicalState.FEVER), 0.3);
			data.put(new Transition<>(MedicalState.FEVER, MedicalState.HEALTHY), 0.4);
			data.put(new Transition<>(MedicalState.FEVER, MedicalState.FEVER), 0.6);
		}
	}

	public enum EmissionProbabilities {
		INSTANCE;

		public final Map<Emission<MedicalState, Symptom>, Double> data;

		EmissionProbabilities() {
			data = new HashMap<>();
			data.put(new Emission<>(MedicalState.HEALTHY, Symptom.NORMAL), 0.5);
			data.put(new Emission<>(MedicalState.HEALTHY, Symptom.COLD), 0.4);
			data.put(new Emission<>(MedicalState.HEALTHY, Symptom.DIZZY), 0.1);
			data.put(new Emission<>(MedicalState.FEVER, Symptom.NORMAL), 0.1);
			data.put(new Emission<>(MedicalState.FEVER, Symptom.COLD), 0.3);
			data.put(new Emission<>(MedicalState.FEVER, Symptom.DIZZY), 0.6);
		}
	}
}
