import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'candidat',
                loadChildren: './candidat/candidat.module#SigecCandidatModule'
            },
            {
                path: 'type-examen',
                loadChildren: './type-examen/type-examen.module#SigecTypeExamenModule'
            },
            {
                path: 'inscription',
                loadChildren: './inscription/inscription.module#SigecInscriptionModule'
            },
            {
                path: 'dispense',
                loadChildren: './dispense/dispense.module#SigecDispenseModule'
            },
            {
                path: 'handicape',
                loadChildren: './handicape/handicape.module#SigecHandicapeModule'
            },
            {
                path: 'type-critere',
                loadChildren: './type-critere/type-critere.module#SigecTypeCritereModule'
            },
            {
                path: 'specialite-option',
                loadChildren: './specialite-option/specialite-option.module#SigecSpecialiteOptionModule'
            },
            {
                path: 'filiere',
                loadChildren: './filiere/filiere.module#SigecFiliereModule'
            },
            {
                path: 'deroulement-scolarite',
                loadChildren: './deroulement-scolarite/deroulement-scolarite.module#SigecDeroulementScolariteModule'
            },
            {
                path: 'type-epreuve',
                loadChildren: './type-epreuve/type-epreuve.module#SigecTypeEpreuveModule'
            },
            {
                path: 'epreuve',
                loadChildren: './epreuve/epreuve.module#SigecEpreuveModule'
            },
            {
                path: 'groupe-epreuve',
                loadChildren: './groupe-epreuve/groupe-epreuve.module#SigecGroupeEpreuveModule'
            },
            {
                path: 'epreuve-specialite-option',
                loadChildren: './epreuve-specialite-option/epreuve-specialite-option.module#SigecEpreuveSpecialiteOptionModule'
            },
            {
                path: 'reclamation',
                loadChildren: './reclamation/reclamation.module#SigecReclamationModule'
            },
            {
                path: 'composition-copie',
                loadChildren: './composition-copie/composition-copie.module#SigecCompositionCopieModule'
            },
            {
                path: 'membre-jury',
                loadChildren: './membre-jury/membre-jury.module#SigecMembreJuryModule'
            },
            {
                path: 'membre-jury-jury',
                loadChildren: './membre-jury-jury/membre-jury-jury.module#SigecMembreJuryJuryModule'
            },
            {
                path: 'type-membre-jury',
                loadChildren: './type-membre-jury/type-membre-jury.module#SigecTypeMembreJuryModule'
            },
            {
                path: 'critere-choix-membre-jury',
                loadChildren: './critere-choix-membre-jury/critere-choix-membre-jury.module#SigecCritereChoixMembreJuryModule'
            },
            {
                path: 'note-membre-critere',
                loadChildren: './note-membre-critere/note-membre-critere.module#SigecNoteMembreCritereModule'
            },
            {
                path: 'surveille',
                loadChildren: './surveille/surveille.module#SigecSurveilleModule'
            },
            {
                path: 'absence',
                loadChildren: './absence/absence.module#SigecAbsenceModule'
            },
            {
                path: 'fraude',
                loadChildren: './fraude/fraude.module#SigecFraudeModule'
            },
            {
                path: 'type-fraude',
                loadChildren: './type-fraude/type-fraude.module#SigecTypeFraudeModule'
            },
            {
                path: 'piece-a-conviction',
                loadChildren: './piece-a-conviction/piece-a-conviction.module#SigecPieceAConvictionModule'
            },
            {
                path: 'sanction',
                loadChildren: './sanction/sanction.module#SigecSanctionModule'
            },
            {
                path: 'etablissement',
                loadChildren: './etablissement/etablissement.module#SigecEtablissementModule'
            },
            {
                path: 'type-etablissement',
                loadChildren: './type-etablissement/type-etablissement.module#SigecTypeEtablissementModule'
            },
            {
                path: 'choix-etablissement',
                loadChildren: './choix-etablissement/choix-etablissement.module#SigecChoixEtablissementModule'
            },
            {
                path: 'enseignant',
                loadChildren: './enseignant/enseignant.module#SigecEnseignantModule'
            },
            {
                path: 'enseigne',
                loadChildren: './enseigne/enseigne.module#SigecEnseigneModule'
            },
            {
                path: 'mention',
                loadChildren: './mention/mention.module#SigecMentionModule'
            },
            {
                path: 'resultat',
                loadChildren: './resultat/resultat.module#SigecResultatModule'
            },
            {
                path: 'session',
                loadChildren: './session/session.module#SigecSessionModule'
            },
            {
                path: 'concours-rattache',
                loadChildren: './concours-rattache/concours-rattache.module#SigecConcoursRattacheModule'
            },
            {
                path: 'option-concours-rattache',
                loadChildren: './option-concours-rattache/option-concours-rattache.module#SigecOptionConcoursRattacheModule'
            },
            {
                path: 'critere-examen',
                loadChildren: './critere-examen/critere-examen.module#SigecCritereExamenModule'
            },
            {
                path: 'retrait-diplome',
                loadChildren: './retrait-diplome/retrait-diplome.module#SigecRetraitDiplomeModule'
            },
            {
                path: 'type-diplome',
                loadChildren: './type-diplome/type-diplome.module#SigecTypeDiplomeModule'
            },
            {
                path: 'type-decision',
                loadChildren: './type-decision/type-decision.module#SigecTypeDecisionModule'
            },
            {
                path: 'centre-examen',
                loadChildren: './centre-examen/centre-examen.module#SigecCentreExamenModule'
            },
            {
                path: 'type-centre-composition',
                loadChildren: './type-centre-composition/type-centre-composition.module#SigecTypeCentreCompositionModule'
            },
            {
                path: 'centre-composition',
                loadChildren: './centre-composition/centre-composition.module#SigecCentreCompositionModule'
            },
            {
                path: 'zone-examen',
                loadChildren: './zone-examen/zone-examen.module#SigecZoneExamenModule'
            },
            {
                path: 'jury',
                loadChildren: './jury/jury.module#SigecJuryModule'
            },
            {
                path: 'salle-composition',
                loadChildren: './salle-composition/salle-composition.module#SigecSalleCompositionModule'
            },
            {
                path: 'village-secteur',
                loadChildren: './village-secteur/village-secteur.module#SigecVillageSecteurModule'
            },
            {
                path: 'commission',
                loadChildren: './commission/commission.module#SigecCommissionModule'
            },
            {
                path: 'ceb',
                loadChildren: './ceb/ceb.module#SigecCebModule'
            },
            {
                path: 'commune',
                loadChildren: './commune/commune.module#SigecCommuneModule'
            },
            {
                path: 'province',
                loadChildren: './province/province.module#SigecProvinceModule'
            },
            {
                path: 'region',
                loadChildren: './region/region.module#SigecRegionModule'
            },
            {
                path: 'centre-compositio-jury',
                loadChildren: './centre-compositio-jury/centre-compositio-jury.module#SigecCentreCompositioJuryModule'
            },
            {
                path: 'etape-examen',
                loadChildren: './etape-examen/etape-examen.module#SigecEtapeExamenModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SigecEntityModule {}
