package mod.remaker.activity.projectwizard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a.a.a.xB;

import mod.hey.studios.util.Helper;
import mod.remaker.activity.projectwizard.model.WizardStep;

import pro.sketchware.R;
import pro.sketchware.databinding.WizardStepBasicBinding;

public class BasicWizardStep extends WizardStep {
    private WizardStepBasicBinding binding;

    @Override
    public String getTitle() {
        return Helper.getResString(R.string.project_wizard_basic_title);
    }

    @Override
    public String getSubtitle() {
        return Helper.getResString(R.string.project_wizard_basic_subtitle);
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        binding = WizardStepBasicBinding.inflate(inflater, container, true);

        binding.btnNext.setOnClickListener(v -> presentStep(new AdditionalWizardStep()));

        binding.tilAppName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_application_name));
        binding.tilPackageName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_package_name));
        binding.tilProjectName.setHint(Helper.getResString(R.string.myprojects_settings_hint_enter_project_name));

        return binding.getRoot();
    }
}
