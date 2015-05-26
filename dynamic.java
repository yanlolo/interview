
public class PhD {
	static int B = 2;
	static int N = 1000;
	static int testTimes = 200000; // test times 

	public static void main(String args[]) {

		System.out.println("Hello, Giovanni Neglia");
		System.out.println("Please wait few seconds...");

		double[] q = new double[N];

		double sum = 0;
		for (int i = 1; i <= N; i++) {
			sum += 1.0 / i;
		}

		for (int i = 1; i <= N; i++) {
			q[i - 1] = 1 / (i * sum);
		}

		double formula = 0;
		double approximateFormula = 0;
		for (int i = 1; i < N; i++) {
			for (int j = i + 1; j <= N; j++) {
				approximateFormula += q[i - 1] * q[j - 1]
						* (q[i - 1] + q[j - 1]) * 2;
				formula += q[i - 1] * q[j - 1] * (q[i - 1] + q[j - 1])
						* (1 / (1 - q[i - 1]) + 1 / (1 - q[j - 1]));
			}
		}

		double staticRate = q[1] + q[2];

		double testRate = getRealHitRate(q);
		

		System.out.println("-- -- -- -- -- -- -- -- --");
		System.out.println("The average tested dynamic hit rate: " + testRate);
		System.out.println("Dynamic hit rate got from the formula: " + formula);
		System.out.println("Dynamic hit rate got from the approximate formula: " + approximateFormula);
		System.out.println("Static hit rate: " + staticRate);
		System.out.println("Congratulate! It's finished.");
	}

	/*
	 * create the request sequence
	 */
	static int getRandom(double[] q) {
		double[] s = new double[N];
		for (int i = 0; i < N; i++) {
			if (i == 0) {
				s[i] = q[0];
			} else {
				s[i] = s[i - 1] + q[i];
			}
		}

		double random = Math.random();
		int test = 0;
		// System.out.println(random);
		for (int i = 0; i < N; i++) {
			if (i == 0) {
				if (random >= 0 && random <= s[i]) {
					test = 1;
				}
			} else {
				if (random >= s[i - 1] && random <= s[i]) {
					test = i + 1;
				}
			}
		}
		return test;
	}

	/*
	 * get the cache
	 */
	static int[] getCache(double[] q) {

		int[] cache = new int[2];
		cache[0] = getRandom(q);

		while (true) {
			cache[1] = getRandom(q);
			if (cache[0] != cache[1]) {
				break;
			}
		}
		return cache;
	}

	/*
	 * the hit rate for a fixed sequence request
	 */
	static double getRealHitRate(double[] q) {
		double test;
		int[] cache = new int[2];
		int hitFrequency = 0;

		for (int i = 0; i < testTimes; i++) {
			cache = getCache(q);
			test = getRandom(q);
			if (test == cache[0] || test == cache[1]) {
				hitFrequency++;
			}
		}

		double testRate = hitFrequency * 1.0 / testTimes;
//		System.out.println("Dynamic: hit " + hitFrequency + " times during "
//				+ testTimes + " times tests");
//		System.out.println("dynamic hit rate: " + testRate);
		return testRate;
	}

}

