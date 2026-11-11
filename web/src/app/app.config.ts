import { ApplicationConfig, provideZonelessChangeDetection } from '@angular/core';
import {provideHttpClient} from '@angular/common/http';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
// Si tu versión no reconoce provideZonelessChangeDetection, usa provideExperimentalZonelessChangeDetection

export const appConfig: ApplicationConfig = {
  providers: [
    provideZonelessChangeDetection(), // Sin Zone.js
    provideRouter(routes),
    provideHttpClient()
  ]
};
