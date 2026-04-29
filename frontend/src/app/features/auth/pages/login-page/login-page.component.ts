import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { FloatLabel } from 'primeng/floatlabel'; // OBLIGATOIRE DANS PRIMENG 19
import { AuthService } from '../../../../core/auth/auth.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  // Ajout de FloatLabel ici
  imports: [ReactiveFormsModule, ButtonModule, InputTextModule, PasswordModule, FloatLabel],
  template: `
    <section class="grid min-h-screen place-items-center px-6">
      <div class="glass-panel w-full max-w-5xl overflow-hidden rounded-[28px] lg:grid lg:grid-cols-[1.05fr_0.95fr] shadow-2xl">
        
        <div class="hidden bg-gradient-to-br from-teal-700 via-teal-800 to-slate-900 p-10 text-white lg:flex lg:flex-col lg:justify-between">
          <div>
            <div class="mb-6 inline-flex rounded-full border border-white/20 px-4 py-2 text-sm backdrop-blur-md">PreventHub</div>
            <h1 class="text-4xl font-semibold leading-tight">Pilot targeted prevention with evidence-backed operations.</h1>
            <p class="mt-5 max-w-lg text-sm text-white/80">
              Campaign orchestration, risk-aware segmentation, communication tracking and impact analytics in one health intelligence workspace.
            </p>
          </div>
          <div class="grid grid-cols-2 gap-4 text-sm">
            <div class="rounded-2xl bg-white/10 p-4 backdrop-blur-sm">
              <div class="text-2xl font-semibold">6</div>
              <div class="text-white/75">Core services</div>
            </div>
            <div class="rounded-2xl bg-white/10 p-4 backdrop-blur-sm">
              <div class="text-2xl font-semibold">4</div>
              <div class="text-white/75">Operational roles</div>
            </div>
          </div>
        </div>

        <div class="p-8 lg:p-12 bg-white dark:bg-slate-900">
          <div class="mb-8">
            <div class="soft-label font-medium uppercase tracking-wider text-teal-600">Secure access</div>
            <h2 class="mt-2 text-3xl font-bold text-slate-800 dark:text-white">Sign in</h2>
          </div>

          <form [formGroup]="form" (ngSubmit)="submit()" class="space-y-6">
            
            <p-floatlabel variant="on">
              <input pInputText id="email" formControlName="email" class="w-full p-3" autocomplete="email" />
              <label for="email">Email address</label>
            </p-floatlabel>

            <p-floatlabel variant="on">
              <p-password 
                inputId="password" 
                formControlName="password" 
                [feedback]="false" 
                [toggleMask]="true" 
                styleClass="w-full" 
                inputStyleClass="w-full p-3">
              </p-password>
              <label for="password">Password</label>
            </p-floatlabel>

            <button 
              pButton 
              type="submit" 
              label="Access platform" 
              class="w-full p-3 mt-4" 
              [disabled]="form.invalid || loading">
            </button>
          </form>

          <div class="mt-8 rounded-2xl bg-slate-50 dark:bg-slate-800 p-4 text-sm text-slate-500 dark:text-slate-400 border border-slate-100 dark:border-slate-700">
            <strong>Demo account:</strong><br/>
            admin&#64;preventhub.io / PreventHub!2026
          </div>
        </div>
      </div>
    </section>
  `
})
export class LoginPageComponent {
  private readonly fb = inject(FormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  loading = false;

  readonly form = this.fb.nonNullable.group({
    email: ['admin@preventhub.io', [Validators.required, Validators.email]],
    password: ['PreventHub!2026', Validators.required]
  });

  submit(): void {
    if (this.form.invalid || this.loading) {
      return;
    }
    this.loading = true;
    const { email, password } = this.form.getRawValue();
    this.authService.login(email, password).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.loading = false;
        // Optionnel : ajouter un toast d'erreur ici plus tard
      }
    });
  }
}