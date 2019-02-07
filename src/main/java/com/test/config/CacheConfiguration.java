package com.test.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.test.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.test.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.test.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.test.domain.Candidat.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Candidat.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Candidat.class.getName() + ".deroulementScolarites", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeExamen.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeExamen.class.getName() + ".etapeExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeExamen.class.getName() + ".concoursRattaches", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeExamen.class.getName() + ".critereExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeExamen.class.getName() + ".specialiteOptions", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeExamen.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName() + ".epreuveSpecialiteOptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName() + ".choixEtablissements", jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName() + ".compositionCopies", jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName() + ".absences", jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName() + ".resultats", jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName() + ".dispenses", jcacheConfiguration);
            cm.createCache(com.test.domain.Inscription.class.getName() + ".fraudes", jcacheConfiguration);
            cm.createCache(com.test.domain.Dispense.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Dispense.class.getName() + ".epreuveSpecialiteOptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Handicape.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Handicape.class.getName() + ".dispenses", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeCritere.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeCritere.class.getName() + ".critereExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.SpecialiteOption.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.SpecialiteOption.class.getName() + ".choixEtablissements", jcacheConfiguration);
            cm.createCache(com.test.domain.SpecialiteOption.class.getName() + ".epreuveSpecialiteOptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Filiere.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Filiere.class.getName() + ".specialiteOptions", jcacheConfiguration);
            cm.createCache(com.test.domain.DeroulementScolarite.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeEpreuve.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeEpreuve.class.getName() + ".epreuves", jcacheConfiguration);
            cm.createCache(com.test.domain.Epreuve.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Epreuve.class.getName() + ".epreuveSpecialiteOptions", jcacheConfiguration);
            cm.createCache(com.test.domain.GroupeEpreuve.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.GroupeEpreuve.class.getName() + ".epreuveSpecialiteOptions", jcacheConfiguration);
            cm.createCache(com.test.domain.EpreuveSpecialiteOption.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.EpreuveSpecialiteOption.class.getName() + ".compositionCopies", jcacheConfiguration);
            cm.createCache(com.test.domain.EpreuveSpecialiteOption.class.getName() + ".absences", jcacheConfiguration);
            cm.createCache(com.test.domain.EpreuveSpecialiteOption.class.getName() + ".dispenses", jcacheConfiguration);
            cm.createCache(com.test.domain.Reclamation.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.CompositionCopie.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.CompositionCopie.class.getName() + ".reclamations", jcacheConfiguration);
            cm.createCache(com.test.domain.CompositionCopie.class.getName() + ".membreJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJury.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJury.class.getName() + ".surveilles", jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJury.class.getName() + ".noteMembreCriteres", jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJury.class.getName() + ".membreJuryJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJury.class.getName() + ".fraudes", jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJury.class.getName() + ".compositionCopies", jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJuryJury.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.MembreJuryJury.class.getName() + ".commissions", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeMembreJury.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeMembreJury.class.getName() + ".membreJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeMembreJury.class.getName() + ".critereChoixMembreJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.CritereChoixMembreJury.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.CritereChoixMembreJury.class.getName() + ".noteMembreCriteres", jcacheConfiguration);
            cm.createCache(com.test.domain.CritereChoixMembreJury.class.getName() + ".typeMembreJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.NoteMembreCritere.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Surveille.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Absence.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Fraude.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Fraude.class.getName() + ".sanctions", jcacheConfiguration);
            cm.createCache(com.test.domain.Fraude.class.getName() + ".pieceAConvictions", jcacheConfiguration);
            cm.createCache(com.test.domain.Fraude.class.getName() + ".membreJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeFraude.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeFraude.class.getName() + ".fraudes", jcacheConfiguration);
            cm.createCache(com.test.domain.PieceAConviction.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Sanction.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Etablissement.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Etablissement.class.getName() + ".enseignes", jcacheConfiguration);
            cm.createCache(com.test.domain.Etablissement.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Etablissement.class.getName() + ".choixEtablissements", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeEtablissement.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeEtablissement.class.getName() + ".etablissements", jcacheConfiguration);
            cm.createCache(com.test.domain.ChoixEtablissement.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Enseignant.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Enseignant.class.getName() + ".enseignes", jcacheConfiguration);
            cm.createCache(com.test.domain.Enseigne.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Mention.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Mention.class.getName() + ".resultats", jcacheConfiguration);
            cm.createCache(com.test.domain.Resultat.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Resultat.class.getName() + ".retraitDiplomes", jcacheConfiguration);
            cm.createCache(com.test.domain.Session.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Session.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Session.class.getName() + ".critereExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.Session.class.getName() + ".enseignes", jcacheConfiguration);
            cm.createCache(com.test.domain.ConcoursRattache.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.ConcoursRattache.class.getName() + ".optionConcoursRattaches", jcacheConfiguration);
            cm.createCache(com.test.domain.OptionConcoursRattache.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.OptionConcoursRattache.class.getName() + ".resultats", jcacheConfiguration);
            cm.createCache(com.test.domain.OptionConcoursRattache.class.getName() + ".critereExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.OptionConcoursRattache.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.CritereExamen.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.RetraitDiplome.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeDiplome.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeDiplome.class.getName() + ".retraitDiplomes", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeDecision.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeDecision.class.getName() + ".resultats", jcacheConfiguration);
            cm.createCache(com.test.domain.CentreExamen.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.CentreExamen.class.getName() + ".zoneExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.TypeCentreComposition.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.TypeCentreComposition.class.getName() + ".centreCompositions", jcacheConfiguration);
            cm.createCache(com.test.domain.CentreComposition.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.CentreComposition.class.getName() + ".centreCompositioJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.CentreComposition.class.getName() + ".etablissements", jcacheConfiguration);
            cm.createCache(com.test.domain.ZoneExamen.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.ZoneExamen.class.getName() + ".centreCompositions", jcacheConfiguration);
            cm.createCache(com.test.domain.Jury.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Jury.class.getName() + ".centreCompositioJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.Jury.class.getName() + ".membreJuryJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.Jury.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.SalleComposition.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.SalleComposition.class.getName() + ".surveilles", jcacheConfiguration);
            cm.createCache(com.test.domain.SalleComposition.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.VillageSecteur.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.VillageSecteur.class.getName() + ".etablissements", jcacheConfiguration);
            cm.createCache(com.test.domain.VillageSecteur.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.Commission.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Commission.class.getName() + ".membreJuryJuries", jcacheConfiguration);
            cm.createCache(com.test.domain.Ceb.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Ceb.class.getName() + ".etablissements", jcacheConfiguration);
            cm.createCache(com.test.domain.Ceb.class.getName() + ".centreCompositions", jcacheConfiguration);
            cm.createCache(com.test.domain.Commune.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Commune.class.getName() + ".villageSecteurs", jcacheConfiguration);
            cm.createCache(com.test.domain.Commune.class.getName() + ".cebs", jcacheConfiguration);
            cm.createCache(com.test.domain.Province.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Province.class.getName() + ".communes", jcacheConfiguration);
            cm.createCache(com.test.domain.Region.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.Region.class.getName() + ".provinces", jcacheConfiguration);
            cm.createCache(com.test.domain.Region.class.getName() + ".critereExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.Region.class.getName() + ".centreExamen", jcacheConfiguration);
            cm.createCache(com.test.domain.CentreCompositioJury.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.CentreCompositioJury.class.getName() + ".salleCompositions", jcacheConfiguration);
            cm.createCache(com.test.domain.CentreCompositioJury.class.getName() + ".inscriptions", jcacheConfiguration);
            cm.createCache(com.test.domain.EtapeExamen.class.getName(), jcacheConfiguration);
            cm.createCache(com.test.domain.EtapeExamen.class.getName() + ".compositionCopies", jcacheConfiguration);
            cm.createCache(com.test.domain.EtapeExamen.class.getName() + ".absences", jcacheConfiguration);
            cm.createCache(com.test.domain.EtapeExamen.class.getName() + ".resultats", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
