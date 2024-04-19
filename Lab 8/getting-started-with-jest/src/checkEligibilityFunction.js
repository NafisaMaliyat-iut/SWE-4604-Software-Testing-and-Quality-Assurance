function checkEligibility(java, cplusplus, ooad){
    //negative and out of scope marks
    if(java <0 || java >100){
        throw new Error("Invalid Java marks") 
    }
    if(cplusplus <0 || cplusplus >100){
        throw new Error("Invalid C++ marks")
    }
    if(ooad <0 || ooad >100){
        throw new Error("Invalid OOAD marks") 
    }

    const basicRequirement = java>=70 && cplusplus>=60 && ooad>=60
    const eligibleForScholarship = basicRequirement && (java + cplusplus + ooad)>=240
    if(eligibleForScholarship){
        return "Eligible for scholarship course"
    }

    const eligibleForNormal = basicRequirement && ((java+cplusplus+ooad)>=220 || (java+cplusplus)>=150)
    if(eligibleForNormal){
        return "Eligible for normal course"
    }
    
    else return "Not Eligible"
}

module.exports = checkEligibility;