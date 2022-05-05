
public class JavaBean {
		private int countPageRefresh;
		private int summEvenOrNegativeNumbers = 0;
		private int summOddOrNegativeNumbers = 0;
		private boolean arrayWithInvalidNumbers = false;

		public int getSummOddOrNegativeNumbers() {
			return summOddOrNegativeNumbers;
		}

		public void setSummOddOrNegativeNumbers(int summOddOrNegativeNumbers) {
			this.summOddOrNegativeNumbers = summOddOrNegativeNumbers;
		}

		public JavaBean(){
			countPageRefresh = 0;
		}

		public int getCountPageRefresh() {
			return countPageRefresh;
		}

		public void setCountPageRefresh(int countPageRefresh) {
			this.countPageRefresh = countPageRefresh;
		}

		public int getSummEvenOrNegativeNumbers() {
			return summEvenOrNegativeNumbers;
		}

		public void setSummEvenOrNegativeNumbers(int summEvenOrNegativeNumbers) {
			this.summEvenOrNegativeNumbers = summEvenOrNegativeNumbers;
		}

		public boolean isArrayWithInvalidNumbers() {
			return arrayWithInvalidNumbers;
		}

		public void setArrayWithInvalidNumbers(boolean arrayWithInvalidNumbers) {
			this.arrayWithInvalidNumbers = arrayWithInvalidNumbers;
		}

	}

