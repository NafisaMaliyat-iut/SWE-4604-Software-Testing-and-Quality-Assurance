const checkEligibility = require("../src/checkEligibilityFunction");

describe("Equivalence Testing", () => {
  describe("Strong Normal", () => {
    describe.each([
      [70, 60, 90, "Eligible for normal course"],
      [70, 80, 60, "Eligible for normal course"],
    ])("should be eligible for normal course", (java, cplusplus, ooad, output) => {
      test(`total is ${java + cplusplus + ooad}`, () => {
        expect(checkEligibility(java, cplusplus, ooad)).toEqual(output);
      });
    });

    describe("should be eligible for scholarship course", () => {
      test("total is 240", () => {
        const java = 80;
        const cplusplus = 80;
        const ooad = 80;
        const output = "Eligible for scholarship course";
        expect(checkEligibility(java, cplusplus, ooad)).toEqual(output);
      });
    });

    describe.each([
      [60, 50, 50, "Not Eligible"],
      [70, 60, 60, "Not Eligible"],
    ])("should be not eligible", (java, cplusplus, ooad, output) => {
      test("cplusplus and ooad marks does not fulfil criteria", () => {
        expect(checkEligibility(java, cplusplus, ooad)).toEqual(output);
      });

      test("total does not fulfill criteria", () => {
        expect(checkEligibility(java, cplusplus, ooad)).toEqual(output);
      });
    });
  });

  describe.each([
    [-10, 80, 80, "Invalid Java marks"],
    [80, -10, 80, "Invalid C++ marks"],
    [80, 80, -10, "Invalid OOAD marks"],
    [110, 80, 80, "Invalid Java marks"],
    [80, 110, 80, "Invalid C++ marks"],
    [80, 80, 110, "Invalid OOAD marks"],
  ])("Weak Robust", (java, cplusplus, ooad, expectedErrorMessage) => {
    test("negative java marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("negative c++ marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("negative ooad marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("out of scope java marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("out of scope c++ marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("out of scope ooad marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
  });

  describe.each([
    [-10, -10, 80, "Invalid Java marks"],
    [80, -10, -10, "Invalid C++ marks"],
    [-10, 80, -10, "Invalid Java marks"],
    [110, 110, 80, "Invalid Java marks"],
    [80, 110, 110, "Invalid C++ marks"],
    [110, 80, 110, "Invalid Java marks"],
  ])("Weak Robust", (java, cplusplus, ooad, expectedErrorMessage) => {
    test("negative java and c++ marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("negative c++ and ooad marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("negative java and ooad marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("out of scope java and c++ marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("out of scope c++ and ooad marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
    test("out of scope java and ooad marks", () => {
      expect(() => checkEligibility(java, cplusplus, ooad)).toThrow(expectedErrorMessage);
    });
  });
});
