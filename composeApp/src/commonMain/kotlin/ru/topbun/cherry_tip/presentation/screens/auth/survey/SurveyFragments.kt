package ru.topbun.cherry_tip.presentation.screens.auth.survey

sealed interface SurveyFragments{

    data object Name: SurveyFragments
    data object Target: SurveyFragments
    data object Gender: SurveyFragments
    data object Age: SurveyFragments
    data object Height: SurveyFragments
    data object Weight: SurveyFragments
    data object TargetWeight: SurveyFragments
    data object Active: SurveyFragments

}
