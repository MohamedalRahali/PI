<?php

namespace App\Form;

use App\Entity\Blogs;
use App\Entity\TypeB;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class BlogsType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre')
            ->add('descr')
            ->add('dateCrea', null, [
                'widget' => 'single_text',
            ])
            ->add('datePub', null, [
                'widget' => 'single_text',
            ])
            ->add('typeBs', EntityType::class, [
                'class' => TypeB::class,
                'choice_label' => 'libelle',
                'multiple' => true,
                'expanded' => false, 
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Blogs::class,
        ]);
    }
}
