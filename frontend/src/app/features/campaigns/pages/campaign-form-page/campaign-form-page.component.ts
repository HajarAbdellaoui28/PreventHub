import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea'; 
import { SelectModule } from 'primeng/select';
import { ApiClientService } from '../../../../core/http/api-client.service';

@Component({
  selector: 'app-campaign-form-page',
  standalone: true,
  imports: [ReactiveFormsModule, ButtonModule, FloatLabel, InputTextModule, TextareaModule, SelectModule],
  template: `
    <section class="space-y-8 p-4">
      <div>
        <div class="section-title text-2xl font-bold">Create campaign</div>
        <div class="soft-label text-gray-500">Define criteria, audience and operational ownership</div>
      </div>

      <form [formGroup]="form" (ngSubmit)="submit()" class="glass-panel grid gap-x-6 gap-y-12 rounded-2xl p-8 lg:grid-cols-2">
        
        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="title" formControlName="title" class="w-full p-3" />
            <label for="title">Title</label>
          </p-floatlabel>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="campaignType" formControlName="campaignType" class="w-full p-3" />
            <label for="campaignType">Campaign type</label>
          </p-floatlabel>
        </div>

        <div class="lg:col-span-2 relative">
          <p-floatlabel variant="on">
            <textarea pTextarea id="objective" formControlName="objective" rows="4" class="w-full p-3"></textarea>
            <label for="objective">Objective</label>
          </p-floatlabel>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="minAge" formControlName="minAge" class="w-full p-3" />
            <label for="minAge">Minimum age</label>
          </p-floatlabel>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="maxAge" formControlName="maxAge" class="w-full p-3" />
            <label for="maxAge">Maximum age</label>
          </p-floatlabel>
        </div>

        <div class="flex flex-col gap-1">
           <label class="text-xs font-medium ml-1 text-gray-400">Gender</label>
           <p-select [options]="genders" formControlName="gender" optionLabel="label" optionValue="value" class="w-full h-[46px]"></p-select>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="expectedPopulation" formControlName="expectedPopulation" class="w-full p-3" />
            <label for="expectedPopulation">Expected population</label>
          </p-floatlabel>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="targetCities" formControlName="targetCities" class="w-full p-3" />
            <label for="targetCities">Target cities (comma separated)</label>
          </p-floatlabel>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="targetRegions" formControlName="targetRegions" class="w-full p-3" />
            <label for="targetRegions">Target regions (comma separated)</label>
          </p-floatlabel>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="targetConditions" formControlName="targetConditions" class="w-full p-3" />
            <label for="targetConditions">Conditions (comma separated)</label>
          </p-floatlabel>
        </div>

        <div class="relative">
          <p-floatlabel variant="on">
            <input pInputText id="ownerOrganization" formControlName="ownerOrganization" class="w-full p-3" />
            <label for="ownerOrganization">Owner organization</label>
          </p-floatlabel>
        </div>

        <div class="lg:col-span-2 flex justify-end mt-6">
          <button pButton type="submit" label="Save campaign" [disabled]="form.invalid" class="px-10 py-3 shadow-lg"></button>
        </div>
      </form>
    </section>
  `
})
export class CampaignFormPageComponent {
  private readonly fb = inject(FormBuilder);
  private readonly api = inject(ApiClientService);
  private readonly router = inject(Router);

  readonly genders = [
    { label: 'All', value: 'ALL' },
    { label: 'Female', value: 'FEMALE' },
    { label: 'Male', value: 'MALE' }
  ];

  readonly form = this.fb.nonNullable.group({
    title: ['', Validators.required],
    objective: ['', Validators.required],
    campaignType: ['SCREENING', Validators.required],
    minAge: ['40', Validators.required],
    maxAge: ['75', Validators.required],
    gender: ['ALL', Validators.required],
    targetCities: ['Casablanca,Rabat', Validators.required],
    targetRegions: ['Casablanca-Settat,Rabat-Sale-Kenitra', Validators.required],
    targetConditions: ['hypertension,diabetes', Validators.required],
    expectedPopulation: ['1800', Validators.required],
    ownerOrganization: ['Ministere de la Sante', Validators.required]
  });

  submit(): void {
    const raw = this.form.getRawValue();
    this.api.createCampaign({
      title: raw.title,
      objective: raw.objective,
      campaignType: raw.campaignType,
      minAge: Number(raw.minAge),
      maxAge: Number(raw.maxAge),
      gender: raw.gender,
      targetCities: split(raw.targetCities),
      targetRegions: split(raw.targetRegions),
      targetConditions: split(raw.targetConditions),
      expectedPopulation: Number(raw.expectedPopulation),
      ownerOrganization: raw.ownerOrganization,
      scheduledDate: new Date().toISOString().slice(0, 10)
    }).subscribe(() => this.router.navigate(['/campaigns']));
  }
}

function split(value: string): string[] {
  return value.split(',').map((part) => part.trim()).filter(Boolean);
}